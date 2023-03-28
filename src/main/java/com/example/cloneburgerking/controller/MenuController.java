package com.example.cloneburgerking.controller;


import com.example.cloneburgerking.dto.MenuRequestDto;
import com.example.cloneburgerking.dto.MenuResponseDto;
import com.example.cloneburgerking.security.UserDetailsImpl;
import com.example.cloneburgerking.service.MenuService;
import com.example.cloneburgerking.service.S3Service;
import com.example.cloneburgerking.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private final S3Service s3Service;


    //S3 업로드
    @PostMapping("/api/upload")
    public ResponseEntity<?> uploadFile(MenuRequestDto menuRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        String url = s3Service.uploadFile(menuRequestDto.getFile());
        menuRequestDto.setUrl(url);
        menuService.save(menuRequestDto,userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body("업로드 성공했습니다.");
    }

    //전체조회
    @GetMapping("/api/menus/list")
    public List<MenuResponseDto> getfilelist() {
        return menuService.getfilelist();
    }

    //S3 및 DB 삭제
    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        menuService.deleteData(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("삭제 성공!");
    }
    //S3 및 DB 수정
    @PatchMapping("/api/update/{id}")
    public ResponseEntity<?> updateMenu
            (@PathVariable Long id, MenuRequestDto requestDto, MultipartFile file, @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws IOException {
            menuService.updateMenu(id, requestDto, file, userDetails.getUser());
            return ResponseEntity.status(HttpStatus.OK).body("수정 성공!");

    }
}
