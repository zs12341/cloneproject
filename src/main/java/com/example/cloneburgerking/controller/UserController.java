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

    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }

    @GetMapping("/index")
    public ModelAndView home() {
    return new ModelAndView("index");
}

    @GetMapping("/login-page")
    public ModelAndView loginPage() {
    return new ModelAndView("login");
}

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

    @GetMapping("/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드
        String createToken = kakaoService.kakaoLogin(code, response);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(jwtUtil.AUTHORIZATION_HEADER, createToken.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/index";
    }

}