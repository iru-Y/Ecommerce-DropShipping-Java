package com.delivery.trizi.trizi.infra.security.domain;

import com.delivery.trizi.trizi.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Document(collection = "ROLES")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleSecurity implements Serializable, UserDetails {

    @Id
    private String id;
    private String login;
    private String password;
    private RoleEnum role;

    public RoleSecurity(User user, RoleEnum roleEnum){
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.role = roleEnum;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == RoleEnum.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
