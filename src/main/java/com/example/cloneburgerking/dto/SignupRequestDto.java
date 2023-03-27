package com.example.cloneburgerking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {


//    @NotBlank(message = "아이디는 필수 입력값입니다.")
//    @Pattern(regexp = "^*[a-z0-9]{4,10}$", message = "4~10글자 알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    private String username;
//    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
//    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}\\[\\]\\|:;\"'<>,.?\\/]).{8,15}$",
//            message = "8~15글자, 글자 1개, 숫자 1개, 특수문자 1개 꼭 입력해야합니다.")
    private String password;

    private String nickname;

    private String email;


    private boolean admin;
    private String adminToken = "";


}