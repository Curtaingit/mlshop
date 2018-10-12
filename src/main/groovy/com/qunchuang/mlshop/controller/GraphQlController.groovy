package com.qunchuang.mlshop.controller

import com.qunchuang.mlshop.graphql.GraphQLExecutor
import com.qunchuang.mlshop.graphql.GraphQLInputQuery
import graphql.ExceptionWhileDataFetching
import graphql.ExecutionResult
import graphql.ExecutionResultImpl
import graphql.GraphQLError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.*

import javax.servlet.http.HttpServletResponse

@RestController
class GraphQlController {

    @Autowired
    private GraphQLExecutor graphQLExecutor;

    @CrossOrigin(origins = "*", methods = [RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS], maxAge = 1800L, allowedHeaders = "*")
    @RequestMapping(path = "/graphql")
    ExecutionResult graphQl(
            @RequestBody GraphQLInputQuery graphQLInput, HttpServletResponse response) throws IOException {

        ExecutionResult result = graphQLExecutor.execute(graphQLInput.getQuery(), graphQLInput.getArguments());

        result = new ExecutionResultBos(result.getData(), result.getErrors(), result.getExtensions());

        //增加response   判断异常中是否包含身份验证异常  返回401 403
        if (result.getErrors() != null) {
            if (result.getErrors().get(0).class.isAssignableFrom(ExceptionWhileDataFetching)) {
                ExceptionWhileDataFetching exceptionWhileDataFetching = result.getErrors().get(0);
                //401
                if (exceptionWhileDataFetching.exception.class.isAssignableFrom(BadCredentialsException.class)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                }
                //403
                if (exceptionWhileDataFetching.exception.class.isAssignableFrom(AccessDeniedException.class)) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        }

        return result;
    }

    @RequestMapping("/graphql2")
    String withoutAuthority(HttpServletResponse response) {
        throw new BadCredentialsException("未登录");
        return "without";
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