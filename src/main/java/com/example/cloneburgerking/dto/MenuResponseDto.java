package com.example.cloneburgerking.dto;

import com.example.cloneburgerking.entity.Menu;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MenuResponseDto {
    private Long id;
    private String title;

    private String url;

    private String price;

    private String category;


    public MenuResponseDto(Menu menu) {
        this.title = menu.getTitle();
        this.url = menu.getS3Url();
        this.category = menu.getCategory();
        this.price = menu.getPrice();
        this.id = menu.getId();
    }
}
