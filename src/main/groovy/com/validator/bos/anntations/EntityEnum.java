package com.validator.bos.anntations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 实体枚举使用
 *
 * @author zzk
 * @date 2018/10/16
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, TYPE})
public @interface EntityEnum {

    /**
     * 枚举要存储到数据库的值
     */
    String value() default "";

    /**
     * 枚举对外的显示描述
     */
    String description() default "";
}
