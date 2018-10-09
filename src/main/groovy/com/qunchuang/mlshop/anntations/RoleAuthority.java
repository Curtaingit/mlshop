package com.qunchuang.mlshop.anntations;


import com.qunchuang.mlshop.enums.RoleAuthorityFunctionEnum;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleAuthority {
    RoleAuthorityFunctionEnum value() default RoleAuthorityFunctionEnum.NO_ROLE;
}
