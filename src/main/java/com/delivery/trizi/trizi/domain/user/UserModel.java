package com.delivery.trizi.trizi.domain.user;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document(collection = "USERS")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements Serializable, UserDetails {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String cpf;
    private String city;
    private String state;
    private String address;
    @Indexed(unique = true)
    @JsonIgnore
    private String password;
    @Indexed(unique = true)
    private String mail;
    private RoleEnum role = RoleEnum.USER;
    private String profileImage;
    private List<ProductModel> favorites = new ArrayList<>();

    public UserModel(String name, String lastName, String cpf, String city, String state, String address, String login, String password, String mail, RoleEnum role, String profileImage) {
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.city = city;
        this.state = state;
        this.address = address;
        this.password = password;
        this.mail = mail;
        this.role = role;
        this.profileImage = profileImage;
    }

    public UserModel(String name, String lastName, String cpf, String city, String state, String address, String login, String password, String mail, RoleEnum role) {
        this.name = name;
        this.lastName = lastName;
        this.cpf = cpf;
        this.city = city;
        this.state = state;
        this.address = address;
        this.password = password;
        this.mail = mail;
        this.role = role;
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
        return this.mail;
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
