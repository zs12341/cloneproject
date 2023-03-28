package com.example.cloneburgerking.controller;

import com.example.cloneburgerking.dto.LoginRequestDto;
import com.example.cloneburgerking.dto.SignupRequestDto;
import com.example.cloneburgerking.jwt.JwtUtil;
import com.example.cloneburgerking.kakao.KakaoService;
import com.example.cloneburgerking.service.UserService;
import com.example.cloneburgerking.status.CustomException;
import com.example.cloneburgerking.status.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Validated @RequestBody SignupRequestDto signupRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(ErrorCode.NOT_FOUND_SIGNUP_USER);
        }
        return userService.signup(signupRequestDto);
    }

//    @ApiOperation(value = "로그인 테스트", notes = "로그인 테스트")
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }


}