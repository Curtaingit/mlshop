package com.qunchuang.mlshop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Curtain
 * @date 2018/10/10 13:42
 */

@Aspect
@Component
@RestController
public class GraphqlAuthorityCheckAspect {

//    @AfterReturning(returning="result", pointcut="execution(public * com.qunchuang.mlshop.graphql.JpaDataFetcher.get(..))")
//    public void after(JoinPoint joinPoint,Object result){
//        System.out.println(result);
//
//    }


    @Pointcut("execution(public * com.qunchuang.mlshop.graphql.JpaDataFetcher.checkPermission(..))")
    public void init() {
    }

    @Before("init()")
    public void doBefore() {

        System.out.println("xxx");

    }
}
