package com.qunchuang.mlshop.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Curtain
 * @date 2018/3/9 15:07
 */

@ControllerAdvice
@Slf4j
public class RwlMallExceptionHandler {

    @ResponseBody
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String handler(AuthenticationException e) throws Exception {

        log.error("Controller : AuthenticationException = " + e.getMessage());
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handler(AccessDeniedException e) throws Exception {
        log.error("Controller : AccessDeniedException = " + e.getMessage());
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handler(Exception e) throws Exception {
        log.error("异常信息:" + e.getMessage());
        log.error("异常堆栈信息:" + collectExceptionStackMsg(e));
        return "系统内部错误，请联系管理员或者添加反馈！";
    }

    /*收集异常堆栈信息*/
    public static String collectExceptionStackMsg(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        return str;
    }
}
