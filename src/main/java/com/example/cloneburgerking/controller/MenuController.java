package com.example.cloneburgerking.controller;


import com.example.cloneburgerking.dto.MenuRequestDto;
import com.example.cloneburgerking.dto.MenuResponseDto;
import com.example.cloneburgerking.dto.SecurityExceptionDto;
import com.example.cloneburgerking.security.UserDetailsImpl;
import com.example.cloneburgerking.service.MenuService;
import com.example.cloneburgerking.service.S3Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarantlr4.v4.runtime.misc.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);


    //S3 업로드
    @PostMapping(value = "/api/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMenu(@RequestPart("requestDto") MenuRequestDto requestDto,
                                        @RequestParam("file") MultipartFile file,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        menuService.save(requestDto, userDetails.getUser(), file);
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("메뉴 등록 성공!", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
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
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("삭제 성공!", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }
    //S3 및 DB 수정
    @PatchMapping(value = "/api/update/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateMenu(@PathVariable("id") Long id,
                                        @RequestPart("requestDto")  MenuRequestDto requestDto,
                                        @RequestPart("file")  MultipartFile file,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        try {
            if (file.getOriginalFilename() == "null" || file.isEmpty() || file == null) { // file이 비어있는 경우
                menuService.textUpdate(id, requestDto, userDetails.getUser());
                SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("텍스트 수정 성공!", HttpStatus.OK.value());
                return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
            }
            menuService.fileUpdate(id, requestDto, userDetails.getUser(), file); // file이 비어있지 않은 경우

            SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("수정 성공!", HttpStatus.OK.value());
            return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
        } catch (Exception e) {
            // 예외 발생 시 로그 출력
            logger.error("Error occurred while updating menu: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred while updating menu");
        }
    }
}
