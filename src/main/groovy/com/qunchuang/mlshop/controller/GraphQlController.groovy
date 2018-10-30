package com.qunchuang.mlshop.controller

import com.qunchuang.mlshop.graphql.*
import com.validator.bos.errors.ValidSelectError
import com.validator.bos.exceptions.MutationValidateException
import graphql.ExceptionWhileDataFetching
import graphql.ExecutionResult
import graphql.ExecutionResultImpl
import graphql.GraphQLError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.*

@RestController
class GraphQlController {

    @Autowired
    private GraphQLExecutor graphQLExecutor;


    @CrossOrigin(origins = "*", methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS], maxAge = 1800L, allowedHeaders = "*")
    @RequestMapping(path = "/graphql")
    ExecutionResult graphQl(
            @RequestBody GraphQLInputQuery graphQLInput) throws IOException {
        ExecutionResult result = graphQLExecutor.execute(graphQLInput.getQuery(), graphQLInput.getArguments());

        Object data = result.getData();
        List<GraphQLError> errors = result.getErrors();
        Map<Object, Object> extensions = result.getExtensions();

        return new ExecutionResultBos(data, parseExceptions(errors), extensions);
    }

    /**
     * 针对各种异常进行解析并简化处理，并返回符合 GraphQL 规范的异常信息。
     * <p>
     * 主要处理的异常信息有3类:
     * 1. graphql 内置的异常信息 -- 原封不动的返回
     * 2. 业务异常 {@link BusinessException} -- 主要返回 code 和 message {@link BusinessExceptionError}
     * 3. 参数校验异常{@link MutationValidateException} -- 返回有且只有一个方法校验失败的信息 {@link MutationValidError}
     *
     * @param errors 要处理的异常信息
     * @return 返回符合 Graphql 规范的异常信息
     */
    private static List<GraphQLError> parseExceptions(List<GraphQLError> errors) {
        List<GraphQLError> list = null;
        //针对不同类型的异常进行处理,因为 result.getErrors()是不可变集合
        if (errors != null) {
            list = new ArrayList<>(errors.size());

            for (GraphQLError error : errors) {
                if (error instanceof ExceptionWhileDataFetching) {
                    ExceptionWhileDataFetching e = (ExceptionWhileDataFetching) error;

                    if (e.getException() instanceof BusinessException) {

                        list.add(new BusinessExceptionError(error, (BusinessException) e.getException()));

                    } else if (e.getException() instanceof MutationValidateException) {
                        ValidSelectError validSelectError = ((MutationValidateException) e.getException()).getError();
                        MutationValidError mutationValidError = new MutationValidError(validSelectError, error);

                        list.add(mutationValidError);
                        //解决401  403问题
                    }else if(e.getException() instanceof AuthenticationException){
                        throw new BadCredentialsException(e.getException().message);
                    }else if (e.getException() instanceof AccessDeniedException){
                        throw new AccessDeniedException(e.getException().message);
                    }
                } else {
                    list.add(error);
                }
            }
        }
        return list;
    }

    /**
     * 为了解决一个数据返回规范的问题，前端用graphql-cli/playground的时候，收到后端数据返回时如果发现有errors字段（不管是不是null，是不是为空数组）
     * 都会认为有错误；而ExecutionResultImpl里的toSpecification里是说如果不为null则肯定要返回，为null才不设置该字段），这两者之间有冲突
     * 到底谁对谁错后面需要研究清楚，然后给相应的项目提issue～ TODO
     * 这里先覆盖一个方法把问题先解决。
     */
    static final class ExecutionResultBos extends ExecutionResultImpl {
        public ExecutionResultBos(Object data, List<? extends GraphQLError> errors, Map<Object, Object> extensions) {
            super(data, errors, extensions);
        }

        @Override
        public List<GraphQLError> getErrors() {
            List<GraphQLError> errors = super.getErrors();
            if (errors != null && errors.size() == 0) {
                return null;
            }
            return errors;
        }
    }
}