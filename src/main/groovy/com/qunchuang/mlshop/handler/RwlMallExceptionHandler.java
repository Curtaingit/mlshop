package com.qunchuang.mlshop.handler;

import com.qunchuang.mlshop.exception.MLShopRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)   //设置返回码
    public String handler(Exception e) throws Exception {
        if (e instanceof MLShopRunTimeException) {
            log.error("Controller : RwlMallException = " + e.getMessage());
            return e.getMessage();
        } else if (e instanceof BadCredentialsException) {
            log.error("Controller : BadCredentialsException = " + e.getMessage());
            throw e;
        } else if (e instanceof AccessDeniedException) {
            log.error("Controller : AccessDeniedException = " + e.getMessage());
            throw e;
        } else if (e instanceof MissingServletRequestParameterException) {
            log.error("Controller : MissingServletRequestParameterException = " + e.getMessage());
            return e.getMessage();
        } else {
            log.error("异常信息:" + e.getMessage());
            log.error("异常堆栈信息:" + collectExceptionStackMsg(e));
            return "系统内部错误，请联系管理员或者添加反馈！";
        }

    }

    /*收集异常堆栈信息*/
    public static String collectExceptionStackMsg(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        return str;
    }
}
