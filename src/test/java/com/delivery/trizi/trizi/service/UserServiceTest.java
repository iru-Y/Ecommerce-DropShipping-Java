package com.delivery.trizi.trizi.service;

import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.storage.StorageService;
import com.delivery.trizi.trizi.repositories.UserRepository;
import com.delivery.trizi.trizi.services.UserService;
import com.delivery.trizi.trizi.services.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetByIdUserExists() {
        String userId = "123";
        UserModel mockUser = new UserModel();
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        UserModel result = userService.getById(userId);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }

    @Test
    public void testGetByIdUserNotFound() {
        String userId = "123";
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getById(userId));
    }

    @Test
    public void testPostUser() {
        UserModel mockUser = new UserModel();
        when(userRepository.save(mockUser)).thenReturn(mockUser);

        UserModel result = userService.post(mockUser);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }


    @Test
    public void testLoadUserByUsernameUserFound() {
        String username = "testuser";
        UserModel mockUser = new UserModel();
        when(userRepository.findByLogin(username)).thenReturn(mockUser);

        UserDetails result = userService.loadUserByUsername(username);

        assertNotNull(result);
        assertEquals(mockUser, result);
    }

}
