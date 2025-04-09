package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.exception.*;
import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.model.UserModel;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPort;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
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
    private static final String ROLE_OWNER_NAME = "PROPIETARIO";
    private static final String ROLE_OWNER_DESCRIPTION = "PROPIETARIO";

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

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

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

    @Test
    void saveUser_SuccessfullyCreatesUser() {
        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.of(ownerRole));
        when(passwordEncoderPort.encode(DEFAULT_PASSWORD)).thenReturn(ENCODED_PASSWORD);
        doNothing().when(userPersistencePort).saveUser(any(UserModel.class));

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));
        verify(userPersistencePort, times(1)).saveUser(userModel);
    }

    @Test
    void saveUser_InvalidEmail_ThrowsException() {
        userModel.setEmail(INVALID_EMAIL);
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.INVALID_EMAIL, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_InvalidPhoneNumber_ThrowsException() {
        userModel.setPhoneNumber(INVALID_PHONE_NUMBER);
        InvalidPhoneNumberException exception = assertThrows(InvalidPhoneNumberException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.INVALID_PHONE, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_InvalidDocumentNumber_ThrowsException() {
        userModel.setDocumentNumber(INVALID_DOCUMENT_NUMBER);
        InvalidDocumentException exception = assertThrows(InvalidDocumentException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.INVALID_DOCUMENT, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_NullRoleId_ThrowsException() {
        userModel.getRoleModel().setId(null);
        MissingRequiredFieldsException exception = assertThrows(MissingRequiredFieldsException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.ROLE_REQUIRED, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_UnderageUser_ThrowsException() {
        userModel.setBirthdate(LocalDate.now().minusYears(UNDERAGE_AGE));
        UnderageUserException exception = assertThrows(UnderageUserException.class, () -> userUseCase.saveUser(userModel));
        assertEquals(ErrorMessages.UNDERAGE_USER, exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any());
    }

    @Test
    void saveUser_ValidRole_ShouldNotThrowException() {
        when(rolePersistencePort.getRoleById(ROLE_OWNER_ID)).thenReturn(Optional.of(ownerRole));
        when(passwordEncoderPort.encode(anyString())).thenReturn(ENCODED_PASSWORD);

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));
    }

    @Test
    void saveUser_InvalidRole_ShouldThrowException() {
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
        verify(userPersistencePort, times(1)).getUserById(DEFAULT_USER_ID);
    }

    @Test
    void getUserById_NonExistingUser_ThrowsUserNotFoundException() {
        when(userPersistencePort.getUserById(DEFAULT_USER_ID)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userUseCase.getUserById(DEFAULT_USER_ID));

        verify(userPersistencePort, times(1)).getUserById(DEFAULT_USER_ID);
    }
}
