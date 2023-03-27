package com.example.cloneburgerking.controller;

import com.example.cloneburgerking.dto.MenuRequestDto;
import com.example.cloneburgerking.dto.MenuResponseDto;
import com.example.cloneburgerking.entity.Menu;
import com.example.cloneburgerking.repository.MenuRepository;
import com.example.cloneburgerking.service.MenuService;
import com.example.cloneburgerking.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3Controller {
    private final MenuRepository menuRepository;
    private final S3Service s3Service;
    private final MenuService menuService;


    //S3 업로드
    @GetMapping("/api/upload")
    public String goToUpload() {
        return "upload";
    }

    @PostMapping("/api/upload")
    public String uploadFile(MenuRequestDto menuRequestDto) throws IOException {
        String url = s3Service.uploadFile(menuRequestDto.getFile());

        menuRequestDto.setUrl(url);
        menuService.save(menuRequestDto);

        return "redirect:/api/list";
    }

    @GetMapping("/api/list")
    public String listPage(Model model) {
        List<Menu> fileList = menuService.getFiles();
        model.addAttribute("fileList", fileList);
        return "list";
    }

    @GetMapping("/api/folder")
    @ResponseBody
    public List<String> listFolder() {
        return s3Service.allFolders();
    }

}

