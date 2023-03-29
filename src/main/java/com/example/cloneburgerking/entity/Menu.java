package com.example.cloneburgerking.entity;



import com.example.cloneburgerking.dto.MenuRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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
    private String fileUrl;

    @Column
    private String price;

    @Column
    private String category;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Menu(MenuRequestDto menuRequestDto, User user) {
        this.title = menuRequestDto.getTitle();
        this.category = menuRequestDto.getCategory();
        this.price = menuRequestDto.getPrice();
        this.user = user;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                '}';
    }

    public void textUpdate(MenuRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.category = requestDto.getCategory();
        this.price = requestDto.getPrice();
    }

}