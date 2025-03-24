package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.api.IUserServicePort;
import com.pragma.usuarios.domain.exception.*;
import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.model.UserModel;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPort;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.domain.utils.ErrorMessages;
import com.pragma.usuarios.domain.utils.ValidationConstants;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.regex.Pattern;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolePersistencePort rolePersistencePort;
    private final IPasswordEncoderPort passwordEncodePort;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(ValidationConstants.EMAIL_REGEX);
    private static final Pattern PHONE_PATTERN = Pattern.compile(ValidationConstants.PHONE_REGEX);
    private static final Pattern DOCUMENT_PATTERN = Pattern.compile(ValidationConstants.DOCUMENT_REGEX);

    public UserUseCase(IUserPersistencePort userPersistencePort, IRolePersistencePort rolePersistencePort, IPasswordEncoderPort passwordEncodePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncodePort = passwordEncodePort;
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public void saveUser(UserModel userModel) {
        validateUser(userModel);

        RoleModel roleModel = rolePersistencePort.getRoleById(userModel.getRoleModel().getId());
        userModel.setRoleModel(roleModel);

        userModel.setPassword(passwordEncodePort.encode(userModel.getPassword()));
        userPersistencePort.saveUser(userModel);
    }

    @Override
    public UserModel getUserById(Long userId) {
        return userPersistencePort.getUserById(userId);
    }

    private void validateUser(UserModel user) {
        validateRequiredFields(user);
        validateFormats(user);
        validateLegalAge(user.getBirthdate());
        validateRole(user.getRoleModel().getId());
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

        Optional.ofNullable(user.getBirthdate())
                .orElseThrow(() -> new MissingRequiredFieldsException(ErrorMessages.BIRTHDATE_REQUIRED));
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

    private void validateRole(Long roleId) {
        if (rolePersistencePort.getRoleById(roleId) == null) {
            throw new InvalidRoleException(ErrorMessages.INVALID_ROLE);
        }
    }
}
