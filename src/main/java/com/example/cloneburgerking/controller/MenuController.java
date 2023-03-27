package com.example.cloneburgerking.controller;

import com.example.cloneburgerking.dto.MenuRequestDto;
import com.example.cloneburgerking.dto.MenuResponseDto;
import com.example.cloneburgerking.security.UserDetailsImpl;
import com.example.cloneburgerking.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    //전체조회
    @GetMapping("/api/menus/list")
    public List<MenuResponseDto> getfilelist() {
        return menuService.getfilelist();
    }
//
//    //메뉴작성
//    @PostMapping("/api/menus")
//    public MenuResponseDto createMenu (@RequestBody MenuRequestDto menuRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return menuService.createMenu(menuRequestDto,userDetails.getUser());
//    }

//    //메뉴수정
//    @PatchMapping("/api/menus/{id}")
//    public MenuResponseDto updateMenu (@PathVariable Long id, MenuRequestDto menuRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return menuService.updateMenu(id, menuRequestDto, userDetails.getUser());
//    }
    //메뉴삭제
    @DeleteMapping("/api/menus/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return menuService.delete(id, userDetails.getUser());
    }
}
