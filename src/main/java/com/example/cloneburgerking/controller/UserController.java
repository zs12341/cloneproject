package com.example.cloneburgerking.controller;

import com.example.cloneburgerking.dto.LoginRequestDto;
import com.example.cloneburgerking.dto.SignupRequestDto;
import com.example.cloneburgerking.service.UserService;
import com.example.cloneburgerking.status.CustomException;
import com.example.cloneburgerking.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;



//    @ApiOperation(value = "회원가입 테스트", notes = "회원가입 테스트")

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

   /* @ResponseBody
    @GetMapping("/kakao")
    public void  kakaoCallback(@RequestParam String code) throws Exception {

        String access_Token = userService.getKaKaoAccessToken(code);
        userService.createKakaoUser(access_Token);

    }*/

}