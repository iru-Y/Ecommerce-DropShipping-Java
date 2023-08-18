package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.user.User;
import com.delivery.trizi.trizi.services.impl.UserServiceImplFlux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
public class UserController {

    @Autowired
    private UserServiceImplFlux userService;


    @GetMapping(value = "/user")
    public Flux<User> getAll () throws InterruptedException {
//        TimeUnit.SECONDS.sleep(10);
        System.out.println("oba");
        return userService.getAll();
    }

    @GetMapping(value = "/user/{id}")
    public Mono<User> getById (@PathVariable String id) throws InterruptedException {

        return userService.getById(id);
    }

    @PostMapping(value = "/user")
    public Mono<User> post (@RequestBody User user) {
       return userService.post(user);
    }

    @GetMapping(value = "/login/{login}")
    public Mono<User> findByLogin (@PathVariable String login) {
        return userService.findByLogin(login);
    }
}
