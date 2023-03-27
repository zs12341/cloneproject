package com.example.cloneburgerking.controller;

import com.example.cloneburgerking.dto.MenuRequestDto;
import com.example.cloneburgerking.dto.MenuResponseDto;
import com.example.cloneburgerking.entity.Menu;
import com.example.cloneburgerking.repository.MenuRepository;
import com.example.cloneburgerking.service.MenuService;
import com.example.cloneburgerking.service.S3Service;
import lombok.RequiredArgsConstructor;
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


//    @PostMapping("/api/{id}/edit")
//    public String editFile(@PathVariable("id") Long id, @RequestPart(name = "file") MultipartFile file) throws IOException {
//        String key = s3Service.getFileKey(id); // 해당 파일의 Key 값을 가져옴
//        String url = s3Service.editFile(key, file); // 파일 수정
//        menuService.updateUrl(id, url); // 해당 메뉴의 URL 값을 업데이트
//        return "redirect:/api/list";
//    }
//
//    @PostMapping("/api/upload")
//    public String uploadFile(@RequestPart(name = "file") MultipartFile file, @RequestPart(name = "menuRequestDto") MenuRequestDto menuRequestDto) throws IOException {
//        String url = s3Service.uploadFile(file);
//
//        menuRequestDto.setUrl(url);
//        menuService.save(menuRequestDto);
//
//        return "redirect:/api/list";
//    }
//    @PostMapping("/api/{id}/delete")
//    public String deleteFile(@PathVariable("id") Long id) {
//        String key = s3Service.getFileKey(id); // 해당 파일의 Key 값을 가져옴
//        s3Service.deleteFile(key); // 파일 삭제
//        menuService.delete(id); // 해당 메뉴를 DB에서 삭제
//        return "redirect:/api/list";
//    }






}