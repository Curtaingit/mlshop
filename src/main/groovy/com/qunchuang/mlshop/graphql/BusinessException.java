package com.qunchuang.mlshop.graphql;

/**
 * BusinessException
 *
 * @author zzk
 * @date 2018/10/26
 */
public class BusinessException extends RuntimeException {


    private String message;

    private int code = 400;

    public int getCode() {
        return code;
    }

    public BusinessException(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }


}
