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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    private final S3Service s3Service;

    @Transactional
    public void save(MenuRequestDto menuRequestDto, User user, MultipartFile file) throws IOException {
        if (!user.getRole().equals(UserEnum.ADMIN)) {
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);
        }
        Menu menu = new Menu(menuRequestDto, user);
        if (file != null) {
            String fileUrl = s3Service.uploadFile(file);
            menu.setFileUrl(fileUrl);
        }
        menuRepository.save(menu);
    }



    //전체메뉴조회
    @Transactional
    public List<MenuResponseDto> getfilelist() {
        List<Menu> menuList = menuRepository.findAll();
        List<MenuResponseDto> menuResponseDto = new ArrayList<>();
        for (Menu menu : menuList) {
            menuResponseDto.add(new MenuResponseDto(menu));
        }
        return menuResponseDto;
    }


    //메뉴삭제
    @Transactional
    public void deleteData(Long id, User user) {
        // id로 데이터를 조회
        Menu menu = getmenu(id);
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);
        // S3에서 해당 파일을 삭제
        String url = menu.getFileUrl();
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        s3Service.deleteFile(fileName);  // S3Service에서 해당 파일을 삭제하는 로직이 구현

        // DB에서 데이터를 삭제
        menuRepository.delete(menu);
    }

    //메뉴수정
    @Transactional
    public ResponseEntity<?> updateMenu(Long id, MenuRequestDto requestDto, User user, MultipartFile file) throws IOException {
        // id로 데이터를 조회
        System.out.println("============id조회=============");
        Menu menu = getmenu(id);
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);
        // S3에서 기존 파일 삭제
        System.out.println("============기존파일삭제=============");
        String url = menu.getFileUrl();
        if (url != null) {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            s3Service.deleteFile(fileName);
        }
        // 수정하고자 하는 내용을 반영하여 Menu 객체를 업데이트
        menu.setTitle(requestDto.getTitle());
        menu.setCategory(requestDto.getCategory());
        menu.setPrice(requestDto.getPrice());
        if (file != null) {
            System.out.println("============파일업로드=============");
            String fileUrl = s3Service.uploadFile(file.getOriginalFilename(), file.getBytes(), file.getContentType());
            menu.setFileUrl(fileUrl);
        }
        System.out.println("============업데이트=============");
        // DB에서 데이터를 업데이트
        menuRepository.save(menu);
        // 수정된 정보를 반환
        return ResponseEntity.status(HttpStatus.OK).body(menu);
    }

    public Menu getmenu(Long id) {
        return menuRepository.findById(id).orElseThrow(
               () -> new CustomException(ErrorCode.NOT_FOUND_DATA));
    }

    //텍스트수정
    @Transactional
    public ResponseEntity<?> textUpdate(Long id, MenuRequestDto requestDto, User user){
        System.out.println("============텍스트수정================");
        Menu menu = getmenu(id);
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);
        menu.textUpdate(requestDto);
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("수정 성공!",HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }
    //파일수정
    @Transactional
    public ResponseEntity<?> fileUpdate(Long id, MenuRequestDto requestDto, User user, MultipartFile file) throws IOException {
        System.out.println("=================파일 수정 ===========");
        Menu menu = getmenu(id);
        if (!user.getRole().equals(UserEnum.ADMIN))
            throw new CustomException(ErrorCode.ROLE_NOT_EXISTS);

        String url = menu.getFileUrl();
        if (url != null) {
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            s3Service.deleteFile(fileName);
        }
        if (file != null) {
            String fileUrl = s3Service.uploadFile(file.getOriginalFilename(), file.getBytes(), file.getContentType());
            menu.setFileUrl(fileUrl);
        }

        menu.setPrice(requestDto.getPrice());
        menu.setTitle(requestDto.getTitle());
        menu.setCategory(requestDto.getCategory());

        menuRepository.save(menu);

        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("수정 성공!", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }


}




