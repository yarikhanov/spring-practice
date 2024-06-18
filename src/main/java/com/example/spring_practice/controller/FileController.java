package com.example.spring_practice.controller;

import com.example.spring_practice.entity.Event;
import com.example.spring_practice.entity.File;
import com.example.spring_practice.entity.User;
import com.example.spring_practice.service.EventService;
import com.example.spring_practice.service.FileService;
import com.example.spring_practice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/file")
public class FileController {

    private final FileService fileService;
    private final EventService eventService;
    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('client:user')")
    public File getFileById(@PathVariable Long id) {
        return fileService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('client:moderator')")
    public List<File> getAllFiles() {
        return fileService.getAll();
    }


    @PostMapping
    @PreAuthorize("hasAuthority('client:user')")
    public File saveFile(@RequestBody MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User user = userService.getUserByUsername(currentUserName).block();


        File uploadFile = fileService.uploadFile(file);
        Event event = new Event().toBuilder()
                .file(uploadFile)
                .user(user)
                .build();
        eventService.addEvent(event);

        return uploadFile;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('client:moderator')")
    public void deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
    }
}
