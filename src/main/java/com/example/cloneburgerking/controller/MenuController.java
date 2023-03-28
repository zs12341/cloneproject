package com.example.cloneburgerking.controller;


import com.example.cloneburgerking.dto.MenuResponseDto;
import com.example.cloneburgerking.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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


    @DeleteMapping("/api/delete/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteData(id);
        return ResponseEntity.noContent().build();
    }
}
