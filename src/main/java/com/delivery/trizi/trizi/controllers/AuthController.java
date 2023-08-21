package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.infra.security.jwtUtils.GeneratedToken;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleLoginDto;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleRegisterDto;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleSecurity;
import com.delivery.trizi.trizi.infra.security.jwtUtils.TokenService;

import com.delivery.trizi.trizi.repositories.SecurityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SecurityRepository securityRepository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RoleLoginDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((RoleSecurity) auth.getPrincipal());

        return ResponseEntity.ok(new GeneratedToken(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RoleRegisterDto data){
        if(this.securityRepository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        RoleSecurity newUser = new RoleSecurity(data.login(), encryptedPassword, data.role());

        this.securityRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}

