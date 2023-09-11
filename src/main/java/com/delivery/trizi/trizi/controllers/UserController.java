package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.services.UserService;
import com.delivery.trizi.trizi.utils.IpAddressUtil;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
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
    public ResponseEntity<UserModel> getByUserID(@PathVariable String userId) throws UnknownHostException {
        UserModel user = userService.getById(userId);
        if (user != null) {
            String profileImageUrl = user.getProfileImage();
            user.setProfileImage(profileImageUrl);

            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserModel> post(@RequestParam("user") String userJson,
                                          @RequestParam("file") MultipartFile file) throws UnknownHostException {
        var userDto = new Gson().fromJson(userJson, UserModel.class);
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        var user = new UserModel(
                userDto.getLogin(),
                encryptedPassword,
                userDto.getMail(),
                userDto.getRole(),
                ""
        );
        UserModel savedUser = userService.post(user);
        UserModel imageLink = userService.post(savedUser.getId(), file);
        savedUser.setProfileImage(imageLink.getProfileImage());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String userId,
                                                @RequestParam("userJson") String userJson,
                                                @RequestParam("file") MultipartFile file) throws UnknownHostException {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userJson, userModel);
        UserModel updatedUser = userService.put(userId, userModel);
        UserModel imageLink = userService.post(userId, file);
        updatedUser.setProfileImage(imageLink.getProfileImage());

        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
    }
}
