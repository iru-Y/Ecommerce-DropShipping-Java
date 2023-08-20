package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.infra.security.domain.RoleSecurity;

import com.delivery.trizi.trizi.repositories.UserRepository;

import com.delivery.trizi.trizi.services.exception.JwtExceptionOnCreated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;


    public RoleSecurity post (RoleSecurity roleSecurity) {
        return userRepository.insert(roleSecurity);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username).orElseThrow(()-> new UsernameNotFoundException("loadUserByUsername"));
    }
}
