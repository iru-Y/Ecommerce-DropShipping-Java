package com.delivery.trizi.trizi.infra.storage;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Log4j2
@Service
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final String uploadDirectory = "assets";


    public String uploadFile(MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("O arquivo está vazio.");
            }
            String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDirectory);
            if (!uploadPath.toFile().exists()) {
                uploadPath.toFile().mkdirs();
            }

            File localFile = new File(uploadPath.toFile(), uniqueFileName);
            FileOutputStream fos = new FileOutputStream(localFile);
            fos.write(file.getBytes());
            fos.close();

            storageRepository.save(new StorageModel(uniqueFileName));

            return uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Erro ao fazer upload do arquivo: " + e.getMessage());
        }
    }

    public byte[] getFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDirectory, fileName);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler arquivo: " + e.getMessage());
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDirectory, fileName);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao excluir arquivo: " + e.getMessage());
        }
    }
}
