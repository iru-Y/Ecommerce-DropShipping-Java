package com.delivery.trizi.trizi.domain.user;

import com.delivery.trizi.trizi.infra.security.jwtUtils.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;

public record UserDto(String login,
                      String name,
                      String lastName,
                      String city,
                      String cpf,
                      String address,
                      String state,
                      String password, String mail, RoleEnum role, String profileImageFileName) {
}
