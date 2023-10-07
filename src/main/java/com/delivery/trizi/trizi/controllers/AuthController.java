package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.infra.security.jwtUtils.GeneratedToken;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleMailDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.security.jwtUtils.TokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "auth")
@AllArgsConstructor
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping(value = "/mail")
    @Operation(summary = "Autenticação por e-mail e senha", description = "Realiza a autenticação do usuário utilizando e-mail e senha," +
            " aonde o retorno é um token JWT")
    public ResponseEntity mail(@RequestBody RoleMailDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.mail(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserModel) auth.getPrincipal());
        return ResponseEntity.ok(new GeneratedToken(token));
    }
}

