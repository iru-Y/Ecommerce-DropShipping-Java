package com.delivery.trizi.trizi.infra.storage;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@RestController
@RequestMapping(value = "/image")
@CrossOrigin(value = "*")
@AllArgsConstructor
public class StorageController {

    private final S3ImageService s3ImageService;
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(s3ImageService.uploadFile(file), HttpStatus.OK);
    }

        @GetMapping("/view/{filename}")
    public ResponseEntity <String> viewFile (@PathVariable String filename) {
        return ResponseEntity.ok().body(s3ImageService.getFileDownloadUrl(filename));
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = s3ImageService.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(s3ImageService.deleteFile(fileName), HttpStatus.OK);
    }
}
