package com.example.spring_practice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.spring_practice.aws.AwsClient;
import com.example.spring_practice.entity.File;
import com.example.spring_practice.entity.Status;
import com.example.spring_practice.exception.FileNotFoundException;
import com.example.spring_practice.repository.EventRepo;
import com.example.spring_practice.repository.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepo fileRepo;

    private  final EventRepo eventRepo;


    public File getById(Long id) {
        return fileRepo.findById(id).orElseThrow(() -> new FileNotFoundException("File not found"));
    }

    public List<File> getAll() {
        return fileRepo.findAll();
    }

    public File uploadFile(MultipartFile uploadFile) {

        try {
            java.io.File tempFile = java.io.File.createTempFile("uploadFile", ".tmp");
            uploadFile.transferTo(tempFile);

            PutObjectRequest request = new PutObjectRequest("my-spring-bucket", uploadFile.getName(), tempFile);
            AmazonS3 s3Client = AwsClient.getAwsClient();
            s3Client.putObject(request);

            File file = File.builder()
                    .status(Status.ACTIVE)
                    .location("https://my-spring-bucket.storage.yandexcloud.net/" + uploadFile.getName())
                    .build();
            return fileRepo.save(file);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void deleteFile(Long id) {
        fileRepo.softDeleteById(id);
        eventRepo.softDeleteByFileId(id);
    }
}
