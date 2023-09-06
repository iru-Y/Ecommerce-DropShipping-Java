package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.user.UserDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.services.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserModel> getUserById(@PathVariable String userId) {
        UserModel user = userService.getById(userId);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<UserModel> createUserWithImage(@RequestParam("userJson") String userJson,
                                                         @RequestParam("file") MultipartFile file) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userJson, userModel);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUserWithImage(userJson, file));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String userId,
                                                @RequestParam("userJson") String userJson,
                                                @RequestParam("file") MultipartFile file) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userJson, userModel);
        UserModel updatedUser = userService.updateUser(userId, userModel, file);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
    }
}

