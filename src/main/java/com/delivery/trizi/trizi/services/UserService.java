package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.storage.StorageService;
import com.delivery.trizi.trizi.repositories.UserRepository;
import com.delivery.trizi.trizi.services.exception.UserNotFoundException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final StorageService storageService;

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public UserModel getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado para o id " + id));
    }
    public UserModel post (UserModel userModel) {
        return userRepository.save(userModel);
    }

    public Optional<UserModel> findByLogin(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login));
    }

    public UserModel put(String userId, UserModel userModel, MultipartFile file) {
        Optional<UserModel> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            UserModel existingUser = existingUserOptional.get();
            existingUser.setLogin(userModel.getLogin());
            existingUser.setMail(userModel.getMail());
            existingUser.setRole(userModel.getRole());

            if (file != null && !file.isEmpty()) {
                String imageLink = storageService.uploadFile(file);
                existingUser.setProfileImage(imageLink);
            }
            return userRepository.save(existingUser);
        }

        return null;
    }

    public boolean deleteUser(String userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            String profileImageLink = user.getProfileImage();
            if (profileImageLink != null && !profileImageLink.isEmpty()) {
                storageService.deleteFile(profileImageLink);
            }

            userRepository.delete(user);
            return true;
        }
        return false;
    }
    public UserModel post(String userJson, MultipartFile file) throws IOException {
        String fileName = storageService.uploadFile(file);
        UserModel userModel = new Gson().fromJson(userJson, UserModel.class);
        userModel.setProfileImage(storageService.getFileDownloadUrl(fileName));
        return userRepository.save(userModel);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }
}