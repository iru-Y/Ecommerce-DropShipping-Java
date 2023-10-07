package com.delivery.trizi.trizi.controllers;

import com.delivery.trizi.trizi.domain.user.UserDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("users")
@AllArgsConstructor
@Tag(name = "Área relacionado aos usuários",
        description = "As informações sobre credenciais também estão envolvidas neste end-point")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Retorno páginado de todos os usuários do sistema",
            description = "Você pode usar os parâmetros ?page={qual a página que você quer navegar}&size={tamanho da lista}")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "Retorna o usuário através do email",
            description = "Aqui você busca por id")
    public ResponseEntity<UserModel> getByUserID(@PathVariable String id) {
        UserModel user = userService.getById(id);
        if (user != null) {
            String profileImageUrl = user.getProfileImage();
            user.setProfileImage(profileImageUrl);

            return ResponseEntity.ok().body(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    @Operation(summary = "Aqui você cria um usuário, sem foto de perfil",
            description = "para adicionar uma foto de perfil, utilize a operação Patch")
    public ResponseEntity<UserModel> post(@RequestBody UserDto userDto
                                          ) {
        UserModel user = new UserModel();
        String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.password());
        BeanUtils.copyProperties(userDto, user);
        user.setPassword(encryptedPassword);
        UserModel savedUser = userService.post(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Aqui você atualiza os dados do usuário, assim como passa a foto de seu perfil",
            description = "Use o parâmetro ou form-data 'user' : '{json do user} | 'file': {faça upload da sua foto aqui}")
    public ResponseEntity<UserModel> updateUser(@PathVariable String id,
                                                @RequestParam("user") UserDto userDto,
                                                @RequestParam("file") MultipartFile file) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        UserModel updatedUser = userService.put(id, userModel, file);
        return ResponseEntity.ok().body(updatedUser);
    }

    @PatchMapping(value = "/image/{mail}")
    @Operation(summary = "Aqui você atualiza os dados do usuário pelo email",
            description = "Use o parâmetro 'file': {faça upload da sua foto aqui}, além de passar o /{mail}")
    public ResponseEntity<UserModel> updateProfImg (@RequestParam (value = "file") MultipartFile file,
                                                    @PathVariable String mail) {
        UserModel updatedUser = userService.put(mail, file);
        return ResponseEntity.ok().body(updatedUser);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Aqui você deleta um usuário")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().body("Usuário excluído com sucesso.");
    }

    @GetMapping("/mail/{mail}")
    @Operation(summary = "Aqui você pega todos os usuários pelo email",
            description = "passar o /{mail}, ele irá retornar um usuário através do email")
    public ResponseEntity<UserModel> getByMail(@PathVariable String mail) {
        UserModel user = userService.findByMail(mail);
            return ResponseEntity.ok().body(user);
    }
    @PatchMapping("/favorites")
    @Operation(summary = "Aqui você adiciona um produto favorito ao seu usuário",
            description = "passe um mail e uma descrição do produto para adicionar, ex: /user/favorites?m=abcdef@hotmail.com&d=pasta de dentes. Isso anexará um produto favorito ao seu usuário")
    public UserModel favorites (@RequestParam (value = "m") String mail, @RequestParam (value = "d") String description) {
        var user = userService.findByMail(mail);
        return userService.favorites(user, description);
    }
}
