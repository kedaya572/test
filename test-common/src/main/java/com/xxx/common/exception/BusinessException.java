package com.xxx.common.exception;

/**
 * 业务异常：用于区分系统异常与业务异常，避免直接抛出 RuntimeException
 */
public class BusinessException extends RuntimeException {

    private final int code;

    public BusinessException(String message) {
        super(message);
        this.code = 500;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
