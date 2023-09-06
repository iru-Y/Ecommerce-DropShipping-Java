package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.user.UserDto;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.storage.S3ImageService;
import com.delivery.trizi.trizi.repositories.UserRepository;
import com.delivery.trizi.trizi.services.exception.UserNotFoundException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final S3ImageService s3ImageService;

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public UserModel getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado para o id " + id));
    }

    public UserModel post(UserModel user) {
        return userRepository.save(user);
    }

    public Optional<UserModel> findByLogin(String login) {
        return Optional.ofNullable(userRepository.findByLogin(login));
    }

    public UserModel updateUser(String userId, UserModel userModel, MultipartFile file) {
        Optional<UserModel> existingUserOptional = userRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            UserModel existingUser = existingUserOptional.get();
            existingUser.setLogin(userModel.getLogin());
            existingUser.setMail(userModel.getMail());
            existingUser.setRole(userModel.getRole());

            if (file != null && !file.isEmpty()) {
                String imageLink = s3ImageService.uploadFile(file);
                existingUser.setProfileImageFileName(imageLink);
            }
            return userRepository.save(existingUser);
        }

        return null;
    }

    public boolean deleteUser(String userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            UserModel user = userOptional.get();
            String profileImageLink = user.getProfileImageFileName();
            if (profileImageLink != null && !profileImageLink.isEmpty()) {
                s3ImageService.deleteFile(profileImageLink);
            }

            userRepository.delete(user);
            return true;
        }
        return false;
    }

    public UserModel createUserWithImage(String userJson, MultipartFile file) {
        UserModel userModel = new Gson().fromJson(userJson, UserModel.class);
        if (file != null && !file.isEmpty()) {
            String imageLink = s3ImageService.getFileDownloadUrl(file.getOriginalFilename());
            userModel.setProfileImageFileName(imageLink);
        }
        return userRepository.save(userModel);
    }
}
