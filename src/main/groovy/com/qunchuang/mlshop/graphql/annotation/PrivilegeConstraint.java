package com.qunchuang.mlshop.graphql.annotation;

import java.lang.annotation.*;

/**
 * @author Curtain
 * @date 2018/10/26 8:58
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PrivilegeConstraint {

    String expression();

}
