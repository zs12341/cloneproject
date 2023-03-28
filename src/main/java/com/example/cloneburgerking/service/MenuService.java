package com.example.cloneburgerking.service;


import com.example.cloneburgerking.dto.MenuRequestDto;
import com.example.cloneburgerking.dto.MenuResponseDto;
import com.example.cloneburgerking.entity.Menu;
import com.example.cloneburgerking.entity.User;
import com.example.cloneburgerking.entity.UserEnum;
import com.example.cloneburgerking.repository.MenuRepository;
import com.example.cloneburgerking.status.CustomException;
import com.example.cloneburgerking.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    private final S3Service s3Service;

    @Transactional
    public void save(MenuRequestDto menuRequestDto, User user) {
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);
        Menu menu = new Menu(menuRequestDto, user);
        menuRepository.save(menu);
    }


    @Transactional
    public List<MenuResponseDto> getfilelist() {
        List<Menu> menuList = menuRepository.findAll();
        List<MenuResponseDto> menuResponseDto = new ArrayList<>();
        for (Menu menu : menuList) {
            menuResponseDto.add(new MenuResponseDto(menu));
        }
        return menuResponseDto;
    }
    @Transactional
    public void deleteData(Long id, User user) {
        // id로 데이터를 조회
        Menu menu = getmenu(id);
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);
        // S3에서 해당 파일을 삭제
        String url = menu.getS3Url();
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        s3Service.deleteFile(fileName);  // S3Service에서 해당 파일을 삭제하는 로직이 구현

        // DB에서 데이터를 삭제
        menuRepository.delete(menu);
    }
    @Transactional
    public MenuResponseDto updateMenu(Long id, MenuRequestDto requestDto, MultipartFile file, User user)
            throws IOException {
        // id로 데이터를 조회
        Menu menu = getmenu(id);
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);
        // S3에서 기존 파일 삭제
        String url = menu.getS3Url();
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        s3Service.deleteFile(fileName);

        // 새로운 파일 업로드
        String fileUrl = s3Service.uploadFile(file);

        // 수정하고자 하는 내용을 반영하여 Menu 객체를 업데이트
        menu.setTitle(requestDto.getTitle());
        menu.setCategory(requestDto.getCategory());
        menu.setPrice(requestDto.getPrice());
        menu.setS3Url(fileUrl);

        // DB에서 데이터를 업데이트
        menuRepository.save(menu);

        // 수정된 정보를 반환
        return new MenuResponseDto(menu);
    }

    private Menu getmenu(Long id) {
        return menuRepository.findById(id).orElseThrow(
               () -> new CustomException(ErrorCode.NOT_FOUND_DATA));
    }
}




