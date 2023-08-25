package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.infra.security.jwtUtils.GeneratedToken;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleLoginDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.security.jwtUtils.TokenService;

import com.delivery.trizi.trizi.services.SecurityService;

import com.delivery.trizi.trizi.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, SecurityService securityService, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RoleLoginDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        log.info("Token gerado");
        return ResponseEntity.ok(new GeneratedToken(token));
    }
}

