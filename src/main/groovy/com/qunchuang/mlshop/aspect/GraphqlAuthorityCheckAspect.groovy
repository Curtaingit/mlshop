package com.qunchuang.mlshop.aspect

import graphql.ExceptionWhileDataFetching
import graphql.ExecutionResult
import graphql.GraphQLError
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
/**
 * @author Curtain
 * @date 2018/10/10 13:42
 */

@Aspect
@Component
public class GraphqlAuthorityCheckAspect {


    @AfterReturning(returning = "result", pointcut = "execution(public * com.qunchuang.mlshop.graphql.JpaDataFetcher.get(..))")
    public void authority(JoinPoint joinPoint, Object result) {
        List<GraphQLError> errors = ((ExecutionResult) result).getErrors();
        if (errors != null) {
            ExceptionWhileDataFetching exceptionWhileDataFetching = errors.get(0);
            if (exceptionWhileDataFetching.class.isAssignableFrom(ExceptionWhileDataFetching)) {
                throw exceptionWhileDataFetching.exception;
            }
        }

    }

}
