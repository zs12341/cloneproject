package com.example.cloneburgerking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor
public class SignupRequestDto {


    @NotBlank(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^*[a-z0-9]{4,10}$", message = "4~10글자 알파벳 소문자(a~z), 숫자(0~9)만 입력 가능합니다.")
    private String username;
    @NotBlank(message = "비밀번호는 필수 입력값 입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={}\\[\\]\\|:;\"'<>,.?\\/]).{8,15}$",
            message = "8~15글자, 글자 1개, 숫자 1개, 특수문자 1개 꼭 입력해야합니다.")
    private String password;
    @NotBlank(message = "닉네임는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[a-z0-9가-힣])[a-z0-9가-힣]{2,16}$", message = "2자 이상 16자 이하, 영어 또는 숫자 또는 한글로 구성되야 합니다")
    private String nickname; //한글 초성 및 모음은 허가하지 않는다.
    @NotBlank(message = "이메일 형식을 맞추서 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[a-zA-Z0-9.-]+\\.[a-zA-Z0-9]+$", message = "이메일 형식을 맞추서 입력해주세요.")
    private String email;


    private boolean admin;
    private String adminToken = "";


}