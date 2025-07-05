package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.exception.*;
import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.model.UserModel;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPort;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
import com.pragma.usuarios.domain.spi.ITokenPort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import com.pragma.usuarios.domain.utils.ErrorMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    private static final long ROLE_OWNER_ID = 2L;
    private static final long ROLE_EMPLOYEE_ID = 3L;
    private static final long ROLE_CLIENT_ID = 4L;

    private static final String ROLE_OWNER_NAME = "PROPIETARIO";
    private static final String ROLE_EMPLOYEE_NAME = "EMPLEADO";
    private static final String ROLE_CLIENT_NAME = "CLIENTE";

    private static final String ROLE_OWNER_DESCRIPTION = "Propietario";
    private static final String ROLE_EMPLOYEE_DESCRIPTION = "Empleado";
    private static final String ROLE_CLIENT_DESCRIPTION = "Cliente";

    private static final long DEFAULT_USER_ID = 1L;
    private static final String DEFAULT_FIRST_NAME = "Juan";
    private static final String DEFAULT_LAST_NAME = "Perez";
    private static final String DEFAULT_DOCUMENT_NUMBER = "123456789";
    private static final String DEFAULT_PHONE_NUMBER = "+573005698325";
    private static final String DEFAULT_EMAIL = "juan.perez@example.com";
    private static final String DEFAULT_PASSWORD = "password123";
    private static final LocalDate DEFAULT_BIRTHDATE = LocalDate.of(2000, 1, 1);
    private static final String ENCODED_PASSWORD = "encodedPassword";

    private static final String INVALID_EMAIL = "invalid-email";
    private static final String INVALID_PHONE_NUMBER = "123abc";
    private static final String INVALID_DOCUMENT_NUMBER = "abc123";
    private static final int UNDERAGE_AGE = 17;

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_OWNER = "ROLE_OWNER";
    private static final String ROLE_CLIENT = "ROLE_CLIENTE";

    private static final String MOCK_TOKEN = "Bearer mock-token";

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @Mock
    private ITokenPort tokenPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel userModel;
    private RoleModel ownerRole;

    @BeforeEach
    void setUp() {
        ownerRole = new RoleModel(ROLE_OWNER_ID, ROLE_OWNER_NAME, ROLE_OWNER_DESCRIPTION);

        userModel = new UserModel();
        userModel.setId(DEFAULT_USER_ID);
        userModel.setFirstName(DEFAULT_FIRST_NAME);
        userModel.setLastName(DEFAULT_LAST_NAME);
        userModel.setDocumentNumber(DEFAULT_DOCUMENT_NUMBER);
        userModel.setPhoneNumber(DEFAULT_PHONE_NUMBER);
        userModel.setBirthdate(DEFAULT_BIRTHDATE);
        userModel.setEmail(DEFAULT_EMAIL);
        userModel.setPassword(DEFAULT_PASSWORD);
        userModel.setRoleModel(ownerRole);
    }

    private void mockTokenWithRole(String role) {
        when(tokenPort.findBearerToken()).thenReturn(Optional.of(MOCK_TOKEN));
        when(tokenPort.getAuthenticatedUserRole(MOCK_TOKEN)).thenReturn(Optional.of(role));
    }

    @Test
    void saveUser_AdminCreatesOwner_Success() {
        mockTokenWithRole(ROLE_ADMIN);

        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.of(ownerRole));
        when(passwordEncoderPort.encode(DEFAULT_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        doNothing().when(userPersistencePort).saveUser(any(UserModel.class));

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));
        verify(userPersistencePort).saveUser(userModel);
    }

    @Test
    void saveUser_OwnerCreatesEmployee_Success() {
        mockTokenWithRole(ROLE_OWNER);
        RoleModel employeeRole = new RoleModel(ROLE_EMPLOYEE_ID, ROLE_EMPLOYEE_NAME, ROLE_EMPLOYEE_DESCRIPTION);
        userModel.setRoleModel(employeeRole);

        when(rolePersistencePort.getRoleById(ROLE_EMPLOYEE_ID)).thenReturn(Optional.of(employeeRole));
        when(passwordEncoderPort.encode(DEFAULT_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        doNothing().when(userPersistencePort).saveUser(any(UserModel.class));

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));
        verify(userPersistencePort).saveUser(userModel);
    }

    @Test
    void saveUser_ClientCreatesClient_Success() {
        mockTokenWithRole(ROLE_CLIENT);
        RoleModel clientRole = new RoleModel(ROLE_CLIENT_ID, ROLE_CLIENT_NAME, ROLE_CLIENT_DESCRIPTION);
        userModel.setRoleModel(clientRole);

        when(rolePersistencePort.getRoleById(ROLE_CLIENT_ID)).thenReturn(Optional.of(clientRole));
        when(passwordEncoderPort.encode(DEFAULT_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        doNothing().when(userPersistencePort).saveUser(any(UserModel.class));

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));
        verify(userPersistencePort).saveUser(userModel);
    }

    @Test
    void saveUser_InvalidEmail_ThrowsException() {
        mockTokenWithRole(ROLE_ADMIN);
        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.of(ownerRole));

        userModel.setEmail(INVALID_EMAIL);

        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.INVALID_EMAIL, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_InvalidPhoneNumber_ThrowsException() {
        mockTokenWithRole(ROLE_ADMIN);

        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.of(ownerRole));

        userModel.setPhoneNumber(INVALID_PHONE_NUMBER);

        InvalidPhoneNumberException exception = assertThrows(InvalidPhoneNumberException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.INVALID_PHONE, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_InvalidDocumentNumber_ThrowsException() {
        mockTokenWithRole(ROLE_ADMIN);
        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.of(ownerRole));
        userModel.setDocumentNumber(INVALID_DOCUMENT_NUMBER);

        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.INVALID_DOCUMENT, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_UnderageUser_ThrowsException() {
        mockTokenWithRole(ROLE_ADMIN);
        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.of(ownerRole));
        userModel.setBirthdate(LocalDate.now().minusYears(UNDERAGE_AGE));

        UnderageUserException exception = assertThrows(UnderageUserException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.UNDERAGE_USER, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_InvalidRole_ShouldThrowException() {
        mockTokenWithRole(ROLE_ADMIN);
        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.empty());

        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.roleNotFound(ROLE_OWNER_ID), exception.getMessage());
    }

    @Test
    void getUserById_ExistingUser_ReturnsUser() {
        when(userPersistencePort.getUserById(DEFAULT_USER_ID)).thenReturn(Optional.of(userModel));

        UserModel foundUser = userUseCase.getUserById(DEFAULT_USER_ID);

        assertNotNull(foundUser);
        assertEquals(DEFAULT_USER_ID, foundUser.getId());
        verify(userPersistencePort).getUserById(DEFAULT_USER_ID);
    }

    @Test
    void getUserById_NonExistingUser_ThrowsUserNotFoundException() {
        when(userPersistencePort.getUserById(DEFAULT_USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.getUserById(DEFAULT_USER_ID));
        verify(userPersistencePort).getUserById(DEFAULT_USER_ID);
    }
}
