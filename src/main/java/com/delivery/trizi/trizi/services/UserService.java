package com.delivery.trizi.trizi.services;

import com.delivery.trizi.trizi.domain.user.UserModel;
import com.delivery.trizi.trizi.infra.storage.StorageService;
import com.delivery.trizi.trizi.repositories.UserRepository;
import com.delivery.trizi.trizi.services.exception.DataBaseException;
import com.delivery.trizi.trizi.services.exception.WrongObjectException;
import com.delivery.trizi.trizi.utils.IpAddressUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

@Log4j2
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final StorageService storageService;
    private final IpAddressUtil ipAddressUtil;

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public UserModel getById(String id){
        if (id != null) {
            return userRepository.findById(id).orElseThrow(
                    () -> new DataBaseException("Não foi possível encontrar o usuário com o ID: " + id)
            );
        }
        log.info("O ID está nulo");
        throw new DataBaseException("Por favor, o ID não pode ser nulo.");
    }

    public UserModel post(UserModel userModel) {
        if (userModel == null) {
            throw new WrongObjectException("O usuário não pode estar nulo.");
        }
        return userRepository.save(userModel);
    }

    public UserModel put(String id, UserModel userModel) {
        UserModel existingUser = getById(id);
        BeanUtils.copyProperties(userModel, existingUser);
        return userRepository.save(existingUser);
    }

    public void deleteUser(String Id) {
        UserModel user = getById(Id);
        userRepository.delete(user);
    }

    public UserModel post(String id, MultipartFile file) throws UnknownHostException, SocketException {
        UserModel user = getById(id);

        if (file != null && !file.isEmpty()) {
            String imageLink = ipAddressUtil.getServerUrl("data/type/") + storageService.uploadFile(file);
            user.setProfileImage(imageLink);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

    public UserModel getByLogin(String login){
        if (login != null) {
            return userRepository.findByLogin(login);
        }
        log.info("O login está nulo");
        throw new DataBaseException("Por favor, o login não pode ser nulo.");
    }
}
