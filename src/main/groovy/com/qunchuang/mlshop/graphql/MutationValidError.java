package com.qunchuang.mlshop.graphql;


import com.validator.bos.errors.ParamInfo;
import com.validator.bos.errors.ValidSelectError;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.List;

/**
 * MutationValidError
 *
 * @author zzk
 * @date 2018/10/26
 */

public class MutationValidError implements GraphQLError {

    private List<ParamInfo> paramErrors;

    private String message;

    private int code = 400;

    private List<Object> path;

    private List<SourceLocation> locations;


    public MutationValidError(ValidSelectError validSelectError, GraphQLError error) {
        this.paramErrors = validSelectError.getParamInfoList();
        this.message = validSelectError.getMessage();

        this.path = error.getPath();
        this.locations = error.getLocations();
    }

    public List<ParamInfo> getParamErrors() {
        return paramErrors;
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
        return ErrorType.ValidationError;
    }


    public int getCode() {
        return code;
    }


    @Override
    public List<Object> getPath() {
        return path;
    }
}
