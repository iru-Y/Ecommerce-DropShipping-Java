package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.repositories.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserModel> getAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<UserModel> getById(String id) {
        return userRepository.findById(id);
    }

    public UserModel post( UserModel user) {
            return userRepository.insert(user);
    }

    public UserModel findByLogin (String login) {
        return userRepository.findByLogin(login);
    }
}
