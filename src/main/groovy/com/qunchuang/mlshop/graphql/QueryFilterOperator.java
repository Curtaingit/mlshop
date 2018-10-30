package com.qunchuang.mlshop.graphql;

import com.bos.domain.BosEnum;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;

/**
 * @author Curtain
 * @date 2018/10/26 9:31
 */
@SchemaDocumentation("查询过滤操作符")
public enum QueryFilterOperator implements BosEnum {
    ISNULL("ISNULL","为空"),
    ISNOTNULL("ISNOTNULL","不为空"),
    GREATTHAN("GREATTHAN","大于"),
    LESSTHAN("LESSTHAN","小于"),
    NOTLESSTHAN("NOTLESSTHAN","不小于"),
    NOTGREATTHAN("NOTGREATTHAN","不大于"),
    EQUEAL("EQUEAL","相等"),
    IN("IN","包含"),
    NOTIN("NOTIN","不包含"),
    NOT("NOT","非"),
    LIKE("LIKE","LIKE");

    private QueryFilterOperator(String value,String name){
        this.ev = new BosEnum.EnumInnerValue(value, name);
    }

    private BosEnum.EnumInnerValue ev = null;

    @Override
    public EnumInnerValue getEnumInnerValue() {
        return this.ev;
    }
}
