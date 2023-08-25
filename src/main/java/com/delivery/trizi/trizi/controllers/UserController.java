package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.user.UserDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.services.SecurityService;
import com.delivery.trizi.trizi.services.UserService;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping
    public Page<UserModel> getAll(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size);
        log.info("Requisitada lista de usuários (página {})...", page);
        return userService.getAll(pageable);
    }

    @GetMapping(value = "/{id}")
    public UserModel getById (@PathVariable String id){
        return userService.getById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<UserModel> post (@RequestBody @Valid UserDto userDto) {

        var encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());
        var newUser = new UserModel().builder()
                .login(userDto.login())
                .mail(userDto.mail())
                .role(userDto.role())
                .password(encryptedPassword)
                .build();

        return ResponseEntity.ok().body(this.securityService.post(newUser));
    }

    @GetMapping(value = "/login/{login}")
    public UserModel findByLogin (@PathVariable String login) {
        return userService.findByLogin(login);
    }
}
