package com.example.cloneburgerking.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_TOKEN(HttpStatus.BAD_REQUEST, "로그인이 필요합니다."),

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다"),
    INVAILD_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다"),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(HttpStatus.UNAUTHORIZED, "권한 정보가 없는 토큰입니다"),

    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "현재 내 계정 정보가 존재하지 않습니다"),

    ROLE_NOT_EXISTS(HttpStatus.UNAUTHORIZED,  "관리자 계정이 아닙니다."),
    ROLE_NOT_PASSWORD(HttpStatus.BAD_REQUEST,"관리자 암호가 틀려 등록이 불가능합니다."),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "로그아웃 된 사용자입니다"),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    NOT_FOUND_USER(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다."),

    NOT_FOUND_USER_ID(HttpStatus.BAD_REQUEST, "이미 가입된 아이디가 존재합니다."),

    NOT_FOUND_BOARD_ADMIN(HttpStatus.BAD_REQUEST, "게시물을 찾을 수 없습니다.(관리자계정)"),
    NOT_FOUND_DATA(HttpStatus.BAD_REQUEST, "해당 데이터를 존재하지 않습니다."),

    NOT_FOUND_SIGNUP_USER(HttpStatus.BAD_REQUEST, "회원가입 형식이 맞지 않습니다.");



    private final HttpStatus httpStatus;
    private final String detail;

}