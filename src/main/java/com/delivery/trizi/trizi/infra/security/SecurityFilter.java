package com.delivery.trizi.trizi.infra.security;

import com.delivery.trizi.trizi.infra.security.jwtUtils.TokenService;
import com.delivery.trizi.trizi.services.SecurityService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SecurityService securityService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);
            var userDetails = securityService.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    public String recoverToken (HttpServletRequest httpServletRequest) {
        var authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer", "");
    }
}
