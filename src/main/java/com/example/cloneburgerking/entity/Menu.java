package com.example.cloneburgerking.entity;



import com.amazonaws.services.s3.AmazonS3;
import com.example.cloneburgerking.dto.MenuRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String s3Url;

    @Column
    private Integer price;
    @Column
    private String category;
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;


    public Menu(MenuRequestDto menuRequestDto, User user) {
        this.title = menuRequestDto.getTitle();
        this.s3Url = menuRequestDto.getUrl();
        this.category = menuRequestDto.getCategory();
        this.price = menuRequestDto.getPrice();
        this.user =user;
    }

    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", s3Url='" + s3Url + '\'' +
                '}';
    }

    public void textUpdate(MenuRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.price = requestDto.getPrice();
    }

}