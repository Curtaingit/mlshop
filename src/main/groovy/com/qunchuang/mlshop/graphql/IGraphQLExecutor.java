package com.qunchuang.mlshop.graphql;

import graphql.ExecutionResult;
import graphql.language.OperationDefinition;
import graphql.language.Type;
import graphql.schema.GraphQLType;
import org.springframework.lang.Nullable;

import javax.transaction.Transactional;
import java.util.Map;

public interface IGraphQLExecutor {

    public GraphQLType getGraphQLType(Type type);

    OperationDefinition getOperationDefinition(String query);

    @Transactional
    public ExecutionResult execute(String query, @Nullable Map<String, Object> arguments);

    /**
     * 获取类型映射信息
     * @return
     */
    IGraphQlTypeMapper getGraphQlTypeMapper();
}
