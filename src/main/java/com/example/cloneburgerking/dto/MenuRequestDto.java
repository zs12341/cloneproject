package com.example.cloneburgerking.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MenuRequestDto {
    private String title;

    private String url;

    private String price;
    private MultipartFile file;
    private String category;


}