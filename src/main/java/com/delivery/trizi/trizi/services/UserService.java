package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.product.ProductModel;
import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.storage.StorageService;
import com.delivery.trizi.trizi.repositories.UserRepository;
import com.delivery.trizi.trizi.services.exception.UserNotFoundException;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final ProductService productService;

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public UserModel getById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado para o id " + id));
    }

    public UserModel post(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public UserModel findByMail(String mail) {
        return userRepository.findByMail(mail);
    }

    public UserModel put(String userId, UserModel userModel, MultipartFile file) {
        Optional<UserModel> existingUserOptional = userRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            UserModel existingUser = existingUserOptional.get();
            existingUser.setMail(userModel.getMail());
            existingUser.setAddress(userModel.getAddress());
            existingUser.setCity(userModel.getCity());
            existingUser.setState(userModel.getState());
            existingUser.setPassword(userModel.getPassword());
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

    public UserModel post(String userJson, MultipartFile file) {
        String fileName = storageService.uploadFile(file);
        UserModel userModel = new Gson().fromJson(userJson, UserModel.class);
        userModel.setProfileImage(storageService.getFileDownloadUrl(fileName));
        return userRepository.save(userModel);
    }

    public UserModel put(String mail, MultipartFile file) {
        UserModel existingUser = userRepository.findByMail(mail);
            String imageLink = storageService.uploadFile(file);
            existingUser.setProfileImage(storageService.getFileDownloadUrl(imageLink));
            return userRepository.save(existingUser);
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Carregou pelo UserDetails");
        return userRepository.findByMail(username);
    }

    public UserModel favorites(UserModel userModel, String productDescription) {
        var user = userRepository.findByMail(userModel.getMail());
        Optional<ProductModel> productOptional = productService.getByDescription(productDescription);
        if (productOptional.isPresent()) {
            ProductModel product = productOptional.get();
            List<ProductModel> favorites = user.getFavorites();
            if (!favorites.contains(product)) {
                favorites.add(product);
            } else {
                favorites.remove(product);
            }
            return userRepository.save(user);
            } else {
                return user;
            }
        }
    }