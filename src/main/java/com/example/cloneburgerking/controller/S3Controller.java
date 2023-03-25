package com.example.cloneburgerking.controller;

import com.example.cloneburgerking.dto.FileDto;
import com.example.cloneburgerking.entity.FileEntity;
import com.example.cloneburgerking.service.FileService;
import com.example.cloneburgerking.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;
    private final FileService fileService;

    @GetMapping("/api/upload")
    public String goToUpload() {
        return "upload";
    }

    @PostMapping("/api/upload")
    public String uploadFile(FileDto fileDto) throws IOException {
        String url = s3Service.uploadFile(fileDto.getFile());

        fileDto.setUrl(url);
        fileService.save(fileDto);

        return "redirect:/api/list";
    }

    @GetMapping("/api/list")
    public String listPage(Model model) {
        List<FileEntity> file = fileService.getFiles();
        model.addAttribute("fileList", file);
        return "list";
    }
    @GetMapping("/api/folder")
    @ResponseBody
    public List<String> listFolder() {
        return s3Service.allFolders();
    }
}