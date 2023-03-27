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


//    메뉴삭제
    @DeleteMapping("/api/menus/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return menuService.delete(id, userDetails.getUser());
    }
}
