package com.deloitte.springboot.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deloitte.springboot.entities.Image;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
public class ImageController {
    @Value("${custom.saving-folder-path}")
    private String savingFolderPath;

    @PostMapping("/images")
    public Image save(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        String fileExtension = ".".concat(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        byte[] nameDigest = MessageDigest.getInstance("SHA256").digest(file.getBytes());
        String hashedFileName = new BigInteger(1, nameDigest).toString(16).concat(fileExtension);
        String hashStringNamePath = this.savingFolderPath.concat(hashedFileName);

        file.transferTo(Path.of(hashStringNamePath));

        return new Image("public/".concat(hashedFileName));
    }
}
