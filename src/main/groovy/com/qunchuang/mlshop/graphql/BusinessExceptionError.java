package com.qunchuang.mlshop.graphql;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;
import java.util.Map;

/**
 * BusinessExceptionError
 *
 * @author zzk
 * @date 2018/10/26
 */
public class BusinessExceptionError implements GraphQLError {

    private List<Object> path;

    private String message;

    private List<SourceLocation> locations;

    private Map<String, Object> specifications;

    private Map<String, Object> extension;

    private int code;


    public BusinessExceptionError(GraphQLError error, BusinessException exception) {
        this.path = error.getPath();
        this.locations = error.getLocations();
        this.specifications = error.toSpecification();
        this.extension = error.getExtensions();
        this.message = exception.getMessage();
        this.code = exception.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return locations;
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    @Override
    public List<Object> getPath() {
        return path;
    }

    @Override
    public Map<String, Object> toSpecification() {
        return specifications;
    }

    public int getCode() {
        return code;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extension;
    }

}
