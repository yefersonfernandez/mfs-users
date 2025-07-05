package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.exception.*;
import com.pragma.usuarios.domain.model.RestaurantEmployeeModel;
import com.pragma.usuarios.domain.model.RestaurantModel;
import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.model.UserModel;
import com.pragma.usuarios.domain.spi.*;
import com.pragma.usuarios.domain.utils.ErrorMessages;
import com.pragma.usuarios.domain.utils.ValidationConstants;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.pragma.usuarios.domain.utils.ValidationConstants.*;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IPasswordEncoderPort passwordEncodePort;
    private final ITokenPort tokenPort;
    private final IRestaurantFeignClientPort restaurantFeignClientPort;
    private final IRestaurantEmployeeFeignClientPort restaurantEmployeeFeignClientPort;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private static final Pattern DOCUMENT_PATTERN = Pattern.compile(DOCUMENT_REGEX);

    public UserUseCase(
            IUserPersistencePort userPersistencePort,
            IRolePersistencePort rolePersistencePort,
            IPasswordEncoderPort passwordEncodePort,
            ITokenPort tokenPort, IRestaurantFeignClientPort restaurantFeignClientPort, IRestaurantEmployeeFeignClientPort restaurantEmployeeFeignClientPort
    ) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncodePort = passwordEncodePort;
        this.rolePersistencePort = rolePersistencePort;
        this.tokenPort = tokenPort;
        this.restaurantFeignClientPort = restaurantFeignClientPort;
        this.restaurantEmployeeFeignClientPort = restaurantEmployeeFeignClientPort;
    }

    @Override
    public void saveUser(UserModel userModel) {
        assignUserRole(userModel);
        validateUser(userModel);
        userModel.setPassword(encodePassword(userModel.getPassword()));
        userPersistencePort.saveUser(userModel);

        if (isEmployeeRole(userModel)) {
            associateEmployeeWithRestaurant(userModel.getEmail());
        }
    }

    @Override
    public UserModel getUserById(Long userId) {
        return userPersistencePort.getUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.userNotFound(userId)));
    }

    private void assignUserRole(UserModel userModel) {
        tokenPort.findBearerToken()
                .flatMap(tokenPort::getAuthenticatedUserRole)
                .map(String::toUpperCase)
                .ifPresentOrElse(role -> {
                    switch (role) {
                        case ROLE_ADMIN -> userModel.setRoleModel(fetchRole(ROLE_ID_OWNER));
                        case ROLE_OWNER -> userModel.setRoleModel(fetchRole(ROLE_ID_EMPLOYEE));
                        default -> userModel.setRoleModel(fetchRole(ROLE_ID_CLIENT));
                    }
                }, () -> userModel.setRoleModel(fetchRole(ROLE_ID_CLIENT)));
    }

    private void validateUser(UserModel user) {
        validateRequiredFields(user);
        validateFormats(user);
        if (isOwnerRole(user)) {
            validateLegalAge(user.getBirthdate());
        }
    }

    private void validateRequiredFields(UserModel user) {
        Optional.ofNullable(user.getFirstName())
                .orElseThrow(() -> new MissingRequiredFieldsException(ErrorMessages.NAME_REQUIRED));

        Optional.ofNullable(user.getEmail())
                .orElseThrow(() -> new MissingRequiredFieldsException(ErrorMessages.EMAIL_REQUIRED));

        Optional.ofNullable(user.getPhoneNumber())
                .orElseThrow(() -> new MissingRequiredFieldsException(ErrorMessages.PHONE_REQUIRED));

        Optional.ofNullable(user.getDocumentNumber())
                .orElseThrow(() -> new MissingRequiredFieldsException(ErrorMessages.DOCUMENT_REQUIRED));

        if (isOwnerRole(user)) {
            Optional.ofNullable(user.getBirthdate())
                    .orElseThrow(() -> new MissingRequiredFieldsException(ErrorMessages.BIRTHDATE_REQUIRED));
        }

        Optional.ofNullable(user.getRoleModel())
                .filter(roleModel -> roleModel.getId() != null)
                .orElseThrow(() -> new MissingRequiredFieldsException(ErrorMessages.ROLE_REQUIRED));
    }

    private boolean isOwnerRole(UserModel user) {
        return user.getRoleModel() != null &&
                user.getRoleModel().getId() == ROLE_ID_OWNER;
    }

    private boolean isEmployeeRole(UserModel user) {
        return user.getRoleModel() != null &&
                user.getRoleModel().getId() == ROLE_ID_EMPLOYEE;
    }

    private void validateFormats(UserModel user) {
        validateEmail(user.getEmail());
        validatePhoneNumber(user.getPhoneNumber());
        validateDocument(user.getDocumentNumber());
    }

    private void validateEmail(String email) {
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidEmailException(ErrorMessages.INVALID_EMAIL);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if (!PHONE_PATTERN.matcher(phoneNumber).matches()) {
            throw new InvalidPhoneNumberException(ErrorMessages.INVALID_PHONE);
        }
    }

    private void validateDocument(String document) {
        if (!DOCUMENT_PATTERN.matcher(document).matches()) {
            throw new InvalidDocumentException(ErrorMessages.INVALID_DOCUMENT);
        }
    }

    private void validateLegalAge(LocalDate birthdate) {
        int age = Period.between(birthdate, LocalDate.now()).getYears();
        if (age < ValidationConstants.LEGAL_AGE) {
            throw new UnderageUserException(ErrorMessages.UNDERAGE_USER);
        }
    }

    private RoleModel fetchRole(Long roleId) {
        return rolePersistencePort.getRoleById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(ErrorMessages.roleNotFound(roleId)));
    }

    private String encodePassword(String password) {
        return passwordEncodePort.encode(password);
    }

    private void associateEmployeeWithRestaurant(String employeeEmail) {
        String bearerToken = tokenPort.findBearerToken()
                .orElseThrow(() -> new UnauthorizedActionException(ErrorMessages.TOKEN_REQUIRED));

        Long ownerId = tokenPort.getAuthenticatedUserId(bearerToken)
                .orElseThrow(() -> new UnauthorizedActionException(ErrorMessages.AUTHENTICATED_USER_ID_NOT_FOUND));

        RestaurantModel ownerRestaurant = restaurantFeignClientPort.getRestaurantByOwnerId(ownerId)
                .orElseThrow(() -> new RestaurantNotFoundException(ErrorMessages.restaurantNotFound(ownerId)));

        UserModel employee = userPersistencePort.getUserByEmail(employeeEmail)
                .orElseThrow(() -> new UserNotFoundException(ErrorMessages.userNotFoundByEmail(employeeEmail)));

        RestaurantEmployeeModel employeeAssignment = new RestaurantEmployeeModel();
        employeeAssignment.setRestaurantId(ownerRestaurant.getId());
        employeeAssignment.setUserId(employee.getId());

        restaurantEmployeeFeignClientPort.saveRestaurantEmployee(employeeAssignment);
    }

}
