package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleSecurity;
import com.delivery.trizi.trizi.repositories.SecurityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements UserDetailsService {

    @Autowired
    private SecurityRepository securityRepository;

    public RoleSecurity post(RoleSecurity roleSecurity) {
        return securityRepository.save(roleSecurity);
    }

    public void delete (String id) {
        securityRepository.deleteById(id);
    }

    public UserDetails findByLogin(String login){
        return securityRepository.findByLogin(login);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return securityRepository.findByLogin(username);
    }
}
