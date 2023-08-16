package com.delivery.trizi.trizi.services.impl;

import com.delivery.trizi.trizi.domain.user.User;
import com.delivery.trizi.trizi.repositories.UserRepository;
import com.delivery.trizi.trizi.services.CRUDImpl;

import com.delivery.trizi.trizi.services.exception.WrongObjectException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements CRUDImpl {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Mono<User> getById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> post(Object object) {
        if (object instanceof User user) {
            return userRepository.insert(user);
        }
        throw new WrongObjectException("O objeto não é uma instância de User");
    }
    public Mono<User> findByLogin (String login) {
        return userRepository.findByLogin(login);
    }
}
