package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.user.User;
import com.delivery.trizi.trizi.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(value = "/user")
    public List<User> getAll () throws InterruptedException {
//        TimeUnit.SECONDS.sleep(10);
        System.out.println("oba");
        return userService.getAll();
    }

    @GetMapping(value = "/user/{id}")
    public User getById (@PathVariable String id) throws InterruptedException {

        return userService.getById(id).orElseThrow();
    }

    @PostMapping(value = "/user")
    public User post (@RequestBody User user) {
       return userService.post(user);
    }

    @GetMapping(value = "/login/{login}")
    public User findByLogin (@PathVariable String login) {
        return userService.findByLogin(login).orElseThrow();
    }
}
