package com.delivery.trizi.trizi.infra.security;

import com.delivery.trizi.trizi.infra.security.jwtUtils.TokenService;
import com.delivery.trizi.trizi.services.SecurityService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Log4j2
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

            if (userDetails == null) {
                logger.info("O userDetails está nulo");
                throw new UsernameNotFoundException("O UserDetails está nulo, token inválido ou expirado");
            }
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    public String recoverToken (HttpServletRequest httpServletRequest) {
        var authHeader = httpServletRequest.getHeader("Authorization");
        if (authHeader == null) return null; logger.warn("o header Authorization está nulo");

        return authHeader.replace("Bearer ", "");
    }
}
