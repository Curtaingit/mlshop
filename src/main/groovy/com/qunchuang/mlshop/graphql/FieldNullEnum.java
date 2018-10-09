package com.qunchuang.mlshop.graphql;

import com.bos.domain.BosEnum;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;


import javax.persistence.Converter;

@SchemaDocumentation("测试是否为空")
public enum FieldNullEnum implements BosEnum {

    ISNULL( "ISNULL", "为空"), NOTNULL("NOTNULL","不为空");

    private FieldNullEnum(String value, String name) {
        this.ev = new EnumInnerValue(value, name);
    }

    private EnumInnerValue ev = null;

    @Override
    public EnumInnerValue getEnumInnerValue() {
        return this.ev;
    }

    @Converter(autoApply = true)
    public static class FieldNullEnumConverter extends BosEnumConverter<FieldNullEnum> {}
}


