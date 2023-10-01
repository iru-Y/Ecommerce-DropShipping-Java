package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.infra.security.jwtUtils.GeneratedToken;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleMailDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.security.jwtUtils.TokenService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping(value = "/mail")
    public ResponseEntity mail(@RequestBody RoleMailDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.mail(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new GeneratedToken(token));
    }
}

