package org.sopt.sweet.global.error.exception;

import org.sopt.sweet.global.error.ErrorCode;

public class InternalServerException extends BusinessException {
    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}