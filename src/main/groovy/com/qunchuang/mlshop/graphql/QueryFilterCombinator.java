package com.qunchuang.mlshop.graphql;

import com.bos.domain.BosEnum;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;

/**
 * @author Curtain
 * @date 2018/10/26 9:31
 */
@SchemaDocumentation("查询表达式组合操作符")
public enum QueryFilterCombinator implements BosEnum {
    AND("AND", "and"),
    OR("OR", "or"),
    NOT("NOT", "!");

    private QueryFilterCombinator(String value, String name) {
        this.ev = new BosEnum.EnumInnerValue(value, name);
    }

    private BosEnum.EnumInnerValue ev = null;

    @Override
    public BosEnum.EnumInnerValue getEnumInnerValue() {
        return this.ev;
    }
}
