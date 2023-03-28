package com.example.cloneburgerking.service;


import com.amazonaws.services.s3.AmazonS3Client;
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

    private final S3Service s3Service;


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
    public void deleteData(Long id) {
        // id로 데이터를 조회
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 데이터가 존재하지 않습니다."));

        // S3에서 해당 파일을 삭제
        String url = menu.getS3Url();
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        s3Service.deleteFile(fileName);  // S3Service에서 해당 파일을 삭제하는 로직이 구현되어 있다고 가정합니다.

        // DB에서 데이터를 삭제
        menuRepository.delete(menu);
    }


}


