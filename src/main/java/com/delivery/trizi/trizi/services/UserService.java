package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.user.User;
import com.delivery.trizi.trizi.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }


    public Optional<User> getById(UUID id) {
        return userRepository.findById(id);
    }


    public User post( User user) {
            return userRepository.save(user);

    }
    public Optional<User> findByLogin (String login) {
        return userRepository.findByLogin(login);
    }
}
