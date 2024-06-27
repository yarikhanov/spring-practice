package com.example.spring_practice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.spring_practice.aws.AwsClient;
import com.example.spring_practice.entity.File;
import com.example.spring_practice.entity.Status;
import com.example.spring_practice.exception.FileNotFoundException;
import com.example.spring_practice.reactive_repo.EventRepo;
import com.example.spring_practice.reactive_repo.FileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepo fileRepo;

    private  final EventRepo eventRepo;


    public Mono<File> getById(Long id) {
        return fileRepo.findById(id);
    }

    public Flux<File> getAll() {
        return fileRepo.findAll();
    }

    public Mono<File> uploadFile(MultipartFile uploadFile) {

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

    public Mono<Void> deleteFile(Long id) {
        return fileRepo.findById(id)
                .flatMap(file -> {
                    if (file != null) {
                        file.setStatus(Status.DELETED);
                        Mono<Void> updatedEvents = eventRepo.findAllByFile_Id(id)
                                .flatMap(event -> {
                                    event.setStatus(Status.DELETED);
                                    return eventRepo.save(event);
                                })
                                .then();
                        return updatedEvents.then(fileRepo.save(file)).then();
                    } else {
                        return Mono.error(new FileNotFoundException("File not found"));
                    }
                });
    }
}
