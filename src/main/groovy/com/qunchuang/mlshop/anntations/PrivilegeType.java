package com.qunchuang.mlshop.anntations;

import java.lang.annotation.*;

/**
 * @author Curtain
 * @date 2018/10/25 11:14
 */

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivilegeType {
    String value();
}
