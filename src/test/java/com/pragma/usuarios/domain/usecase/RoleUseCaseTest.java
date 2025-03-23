package com.pragma.usuarios.domain.usecase;

import com.pragma.usuarios.domain.model.RoleModel;
import com.pragma.usuarios.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleUseCaseTest {

    private static final long DEFAULT_ROLE_ID = 1L;
    private static final String DEFAULT_ROLE_NAME = "ADMIN";
    private static final String DEFAULT_ROLE_DESCRIPTION = "ADMIN";

    @Mock
    private IRolePersistencePort rolePersistencePort;

    @InjectMocks
    private RoleUseCase roleUseCase;

    private RoleModel roleModel;

    @BeforeEach
    void setUp() {
        roleModel = new RoleModel(DEFAULT_ROLE_ID, DEFAULT_ROLE_NAME, DEFAULT_ROLE_DESCRIPTION);
    }

    @Test
    void saveRole_SuccessfullySavesRole() {
        doNothing().when(rolePersistencePort).saveRole(any(RoleModel.class));

        assertDoesNotThrow(() -> roleUseCase.saveRole(roleModel));
        verify(rolePersistencePort, times(1)).saveRole(roleModel);
    }

    @Test
    void getRoleById_ExistingRole_ReturnsRole() {
        when(rolePersistencePort.getRoleById(DEFAULT_ROLE_ID)).thenReturn(roleModel);

        RoleModel foundRole = roleUseCase.getRoleById(DEFAULT_ROLE_ID);

        assertNotNull(foundRole);
        assertEquals(DEFAULT_ROLE_ID, foundRole.getId());
        assertEquals(DEFAULT_ROLE_NAME, foundRole.getName());
        verify(rolePersistencePort, times(1)).getRoleById(DEFAULT_ROLE_ID);
    }

    @Test
    void getRoleById_NonExistingRole_ReturnsNull() {
        when(rolePersistencePort.getRoleById(DEFAULT_ROLE_ID)).thenReturn(null);

        RoleModel foundRole = roleUseCase.getRoleById(DEFAULT_ROLE_ID);

        assertNull(foundRole);
        verify(rolePersistencePort, times(1)).getRoleById(DEFAULT_ROLE_ID);
    }
}
