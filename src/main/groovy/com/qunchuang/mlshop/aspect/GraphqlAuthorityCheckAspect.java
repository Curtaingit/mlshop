package com.qunchuang.mlshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author Curtain
 * @date 2018/10/10 13:42
 */

@Aspect
@Component
public class GraphqlAuthorityCheckAspect {

    @After("execution(public * com.qunchuang.mlshop.controller.GraphQlController.graphQl(..))")
    public void after(JoinPoint joinPoint){
        System.out.println("aop");

    }


}
