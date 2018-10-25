package com.qunchuang.mlshop.anntations;

import java.lang.annotation.*;

/**
 * 用于模型类  设置账号可读性
 * @author Curtain
 * @date 2018/10/25 10:43
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccountType {
    String value();
}
