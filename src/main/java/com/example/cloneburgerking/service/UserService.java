package com.example.cloneburgerking.service;

import com.example.cloneburgerking.dto.LoginRequestDto;
import com.example.cloneburgerking.dto.SecurityExceptionDto;
import com.example.cloneburgerking.dto.SignupRequestDto;
import com.example.cloneburgerking.entity.User;
import com.example.cloneburgerking.entity.UserEnum;
import com.example.cloneburgerking.jwt.JwtUtil;
import com.example.cloneburgerking.repository.UserRepository;
import com.example.cloneburgerking.status.CustomException;
import com.example.cloneburgerking.status.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final String ADMIN_TOKEN = "ABCD";

//회원가입
    @Transactional
    public ResponseEntity<SecurityExceptionDto> signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = passwordEncoder.encode(signupRequestDto.getPassword());
        String nickname = signupRequestDto.getNickname();
        String email = signupRequestDto.getEmail();

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_USER_ID);
        }
        // 사용자 ROLE 확인
        UserEnum role = UserEnum.USER;
        if (signupRequestDto.isAdmin()) {
            if (!signupRequestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserEnum.ADMIN;
        }


        User user = new User(username,password,role,nickname,email);

        userRepository.save(user);

        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("회원가입 성공!", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }
//로그인
    @Transactional(readOnly = true)
    public ResponseEntity<SecurityExceptionDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorCode.NOT_FOUND_USER)
        );

        // 비밀번호 확인
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw  new CustomException(ErrorCode.NOT_FOUND_USER);
        }
        SecurityExceptionDto securityExceptionDto = new SecurityExceptionDto("로그인 성공!", HttpStatus.OK.value());
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(),user.getNickname(),user.getRole()));

        return ResponseEntity.status(HttpStatus.OK).body(securityExceptionDto);
    }

}