package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.infra.security.domain.GeneratedToken;
import com.delivery.trizi.trizi.infra.security.domain.RoleLoginDto;
import com.delivery.trizi.trizi.infra.security.domain.RoleRegisterDto;
import com.delivery.trizi.trizi.infra.security.domain.RoleSecurity;
import com.delivery.trizi.trizi.infra.security.domain.TokenService;

import com.delivery.trizi.trizi.services.SecurityService;

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
public class RoleController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private TokenService tokenService;
    @PostMapping(value = "/login")
    public ResponseEntity login (@RequestBody RoleLoginDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((RoleSecurity) auth.getPrincipal());
        return ResponseEntity.ok(new GeneratedToken(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<RoleSecurity> register (@RequestBody RoleRegisterDto registerDto) {

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        var roleUsers = new RoleSecurity(registerDto.login(), encryptedPassword, registerDto.role());

//        if (this.securityService(registerDto.login()) != null) {
//            System.out.println("deu erro");
//            return ResponseEntity.badRequest().build();
//
//        }

        return ResponseEntity.ok(this.securityService.post((roleUsers)));
    }


}
