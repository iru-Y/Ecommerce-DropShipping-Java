package com.delivery.trizi.trizi.domain.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.Getter;

import lombok.NoArgsConstructor;

import lombok.NonNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
@Document(collection = "USERS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    private String id;
    @Indexed(unique = true)
    private String login;
    private String password;
    private String mail;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
