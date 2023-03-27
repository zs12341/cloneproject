package com.example.cloneburgerking.service;


import com.example.cloneburgerking.dto.MenuRequestDto;
import com.example.cloneburgerking.dto.MenuResponseDto;

import com.example.cloneburgerking.dto.SecurityExceptionDto;
import com.example.cloneburgerking.entity.Menu;
import com.example.cloneburgerking.entity.User;
import com.example.cloneburgerking.entity.UserEnum;
import com.example.cloneburgerking.repository.MenuRepository;
import com.example.cloneburgerking.status.CustomException;
import com.example.cloneburgerking.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    public void save(MenuRequestDto menuRequestDto) {
        System.out.println("---------file Service-----------");
        Menu menu = new Menu
                (menuRequestDto);
        menuRepository.save(menu);
    }

    public List<Menu> getFiles() {
        List<Menu> listall = menuRepository.findAll();
        return listall;
    }

    @Transactional
    public List<MenuResponseDto> getfilelist() {
        System.out.println("-----------ㅇㅇㅇㅇㅇㅇ------");
        List<Menu> menuList = menuRepository.findAll();
        List<MenuResponseDto> menuResponseDto = new ArrayList<>();
        for (Menu menu : menuList) {
            menuResponseDto.add(new MenuResponseDto(menu));
        }
        return menuResponseDto;
    }

//    @Transactional
//    public MenuResponseDto createMenu (MenuRequestDto menuRequestDto, User user) {
//        if (!user.getRole().equals(UserEnum.ADMIN))
//            throw new CustomException(ErrorCode.NOT_FOUND_USER);
//        Menu menu = menuRepository.saveAndFlush(new Menu(menuRequestDto, user));
//        return new MenuResponseDto(menu);
//    }

//    @Transactional
//    public MenuResponseDto updateMenu (Long id, MenuRequestDto menuRequestDto, User user) {
//        Menu menu = menuRepository.findById(id).orElseThrow(
//                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
//        );
//        if (!user.getRole().equals(UserEnum.ADMIN))
//            throw new CustomException(ErrorCode.NOT_FOUND_USER);
//        menu.updateMenu(menuRequestDto);
//        return new MenuResponseDto(menu);
//    }

    @Transactional
    public ResponseEntity<?> delete (Long id, User user) {
        Menu menu = menuRepository.findById(id).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.NOT_FOUND_USER);
        menuRepository.deleteById(id);
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("메뉴 삭제 성공!", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }

//    public void updateUrl(Long id, String url) {
//        Menu menu = menuRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid menu id"));
//        menu.setS3Url(url);
//        menuRepository.save(menu);
//    }
//
//    public void delete(Long id) {
//        menuRepository.deleteById(id);
//    }
}