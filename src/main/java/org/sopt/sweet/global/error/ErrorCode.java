package org.sopt.sweet.global.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    /**
     * 400 Bad Request
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NAME_LENGTH_EXCEEDED(HttpStatus.BAD_REQUEST, "이름은 10글자를 초과할 수 없습니다."),
    MEMBER_NUMBER_EXCEEDED(HttpStatus.BAD_REQUEST, "해당 선물방의 최대 인원을 초과하였습니다."),
    INVITATION_CLOSED(HttpStatus.BAD_REQUEST, "초대가 마감되었습니다."),
    MEMBER_GIFT_COUNT_EXCEEDED(HttpStatus.BAD_REQUEST, "최대 선물 등록 개수를 초과하였습니다."),
    TOURNAMENT_START_DATE_PASSED(HttpStatus.BAD_REQUEST, "토너먼트 시작 날짜가 지났습니다"),
    ROOM_OWNER_CANNOT_DELETE_SELF(HttpStatus.BAD_REQUEST, "방 개설자는 스스로를 삭제할 수 없습니다."),
    ALREADY_PARTICIPATED_TOURNAMENT(HttpStatus.BAD_REQUEST, "이미 참가한 토너먼트입니다."),
    /**
     * 401 Unauthorized
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "리소스 접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰의 형식이 올바르지 않습니다. Bearer 타입을 확인해 주세요."),
    INVALID_ACCESS_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "액세스 토큰의 값이 올바르지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다. 재발급 받아주세요."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 형식이 올바르지 않습니다."),
    INVALID_REFRESH_TOKEN_VALUE(HttpStatus.UNAUTHORIZED, "리프레시 토큰의 값이 올바르지 않습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 만료되었습니다. 다시 로그인해 주세요."),
    NOT_MATCH_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "일치하지 않는 리프레시 토큰입니다."),
    /**
     * 403 Forbidden
     */
    FORBIDDEN(HttpStatus.FORBIDDEN, "리소스 접근 권한이 없습니다."),
    MEMBER_NOT_IN_ROOM(HttpStatus.FORBIDDEN, "해당 선물 방에 존재하지 않는 멤버입니다."),
    MEMBER_NOT_GIFT_OWNER(HttpStatus.FORBIDDEN, "해당 선물을 등록하지 않은 멤버입니다."),
    ROOM_OWNER_MISMATCH(HttpStatus.FORBIDDEN, "해당 선물방의 개설자가 아닙니다."),

    /**
     * 404 Not Found
     */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "엔티티를 찾을 수 없습니다."),
    OPEN_GRAPH_NOT_FOUND(HttpStatus.NOT_FOUND, "오픈 그래프 정보를 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 멤버를 찾을 수 없습니다."),
    ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 선물방을 찾을 수 없습니다."),
    GIFT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 선물을 찾을 수 없습니다."),

    /**
     * 405 Method Not Allowed
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 HTTP method 요청입니다."),

    /**
     * 409 Conflict
     */
    CONFLICT(HttpStatus.CONFLICT, "이미 존재하는 리소스입니다."),
    MEMBER_ALREADY_EXISTS_ROOM(HttpStatus.CONFLICT, "해당 선물방에 이미 참여하였습니다."),
    DUPLICATED_GIFT(HttpStatus.CONFLICT, "중복된 선물 등록입니다."),

    /**
     * 500 Internal Server Error
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
