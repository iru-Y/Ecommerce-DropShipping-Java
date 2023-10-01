package com.delivery.trizi.trizi.service;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleEnum;
import com.delivery.trizi.trizi.infra.storage.StorageService;
import com.delivery.trizi.trizi.repositories.UserRepository;
import com.delivery.trizi.trizi.services.ProductService;
import com.delivery.trizi.trizi.services.UserService;
import com.delivery.trizi.trizi.services.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductService productService;
    @Mock
    private StorageService storageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        MockitoAnnotations.openMocks(this);
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
    @Test
    void testFavorites() {
        UserModel user = new UserModel("yuri", "Doe", "123456789", "City", "State", "Address", "yuri", "password", "john@example.com", RoleEnum.USER);
        when(userRepository.findByLogin("yuri")).thenReturn(user);

        List<ProductModel> product = List.of(new ProductModel("6514fe26802ffd290adbef00", "333cachorro-quente", 10L, "1000.00", "laptop.jpg"));
        when(productService.getByDescription("333cachorro-quente")).thenReturn(product);
        var products = product.stream().map(x-> x.getQuantity()).toList().toString();
        var updatedUser = userService.favorites(user, products);

        assertNotNull(updatedUser);
        assertEquals(1, updatedUser.getFavorites().size());
        assertEquals("Laptop", updatedUser.getFavorites().get(0).getDescription());
    }
}
