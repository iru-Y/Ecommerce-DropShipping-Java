package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.user.UserDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
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

    @GetMapping("/id/{id}")
    public ResponseEntity<UserModel> getByUserID(@PathVariable String id) {
        UserModel user = userService.getById(id);
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
                                          @RequestParam("file") MultipartFile file) throws Exception {
        log.info("entrou no post");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.post(userJson, file));
    }
    @PostMapping(value = "/nodata")
    public ResponseEntity<UserModel> post(@RequestBody UserDto userDto
                                          ) {

        UserModel user = new UserModel();
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(encryptedPassword);
        UserModel savedUser = userService.post(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String id,
                                                @RequestParam("user") String userJson,
                                                @RequestParam("file") MultipartFile file) throws Exception {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userJson, userModel);
        UserModel updatedUser = userService.put(id, userModel, file);
        UserModel imageLink = userService.post(id, file);
        updatedUser.setProfileImage(imageLink.getProfileImage());

        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
    }

    @GetMapping("/login/{login}")
    public ResponseEntity<UserModel> getByLogin(@PathVariable String login) {
        UserModel user = (UserModel) userService.loadUserByUsername(login);
        if (user != null) {
            String profileImageUrl = user.getProfileImage();
            user.setProfileImage(profileImageUrl);
            log.info("Entrou no getByLogin");
            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
