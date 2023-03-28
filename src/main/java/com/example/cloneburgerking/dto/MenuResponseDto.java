package com.example.cloneburgerking.dto;

import com.example.cloneburgerking.entity.Menu;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class MenuResponseDto {
    private Long id;
    private String title;

    private String url;

    private Integer price;

    private String category;


    public MenuResponseDto(Menu menu) {
        this.title = menu.getTitle();
        this.url = menu.getS3Url();
        this.category = menu.getCategory();
        this.price = menu.getPrice();
        this.id = menu.getId();
    }
}
