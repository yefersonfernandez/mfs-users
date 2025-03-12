package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.exception.*;
import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.model.UserModel;
import com.pragma.usuarios.domain.spi.IPasswordEncoderPort;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
import com.pragma.usuarios.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

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
        ownerRole = new RoleModel(2L, "PROPIETARIO","PROPIETARIO");

        userModel = new UserModel();
        userModel.setFirstName("Juan");
        userModel.setLastName("Perez");
        userModel.setDocumentNumber("123456789");
        userModel.setPhoneNumber("+573005698325");
        userModel.setBirthdate(LocalDate.of(2000, 1, 1));
        userModel.setEmail("juan.perez@example.com");
        userModel.setPassword("password123");
        userModel.setRoleModel(new RoleModel(2L, "PROPIETARIO","PROPIETARIO"));
    }

    @Test
    void saveUser_SuccessfullyCreatesOwner() {
        when(rolePersistencePort.getRoleById(2L)).thenReturn(ownerRole);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));

        assertEquals(ownerRole, userModel.getRoleModel());
        assertEquals("encodedPassword", userModel.getPassword());

        verify(userPersistencePort, times(1)).saveUser(userModel);
    }

    @Test
    void saveUser_InvalidEmail_ThrowsException() {
        userModel.setEmail("invalid-email");
        assertThrows(InvalidEmailException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void saveUser_InvalidPhoneNumber_ThrowsException() {
        userModel.setPhoneNumber("123abc");
        assertThrows(InvalidPhoneNumberException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void saveUser_InvalidDocumentNumber_ThrowsException() {
        userModel.setDocumentNumber("abc123");
        assertThrows(InvalidDocumentException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void saveUser_UnderageUser_ThrowsException() {
        userModel.setBirthdate(LocalDate.now().minusYears(17));
        assertThrows(UnderageUserException.class, () -> userUseCase.saveUser(userModel));
    }

    @Test
    void saveUser_ValidRole_ShouldNotThrowException() {
        when(rolePersistencePort.getRoleById(2L)).thenReturn(ownerRole);
        when(passwordEncoderPort.encode(anyString())).thenReturn("encodedPassword");

        assertDoesNotThrow(() -> userUseCase.saveUser(userModel));
    }

    @Test
    void saveUser_InvalidRole_ShouldThrowException() {
        when(rolePersistencePort.getRoleById(2L)).thenReturn(null);

        assertThrows(InvalidRoleException.class, () -> userUseCase.saveUser(userModel));
    }
}
