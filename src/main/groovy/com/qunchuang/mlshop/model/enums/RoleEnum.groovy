package com.qunchuang.mlshop.model.enums

import com.bos.domain.BosEnum
import com.qunchuang.mlshop.graphql.BosEnumConverter
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation


import javax.persistence.Converter

@SchemaDocumentation("类型,当前类型分为自营/合作，对应回传信息为own/coporate；")
enum RoleEnum implements BosEnum {
    SUPERADMIN("SUPERADMIN", "超级管理员"),
    ADMIN("ADMIN", "业务经理" );

    private RoleEnum(String value, String name, String description) {
        this.ev = new BosEnum.EnumInnerValue(value, name);
    }

    private BosEnum.EnumInnerValue ev = null;

    @Override
    public BosEnum.EnumInnerValue getEnumInnerValue() {
        return this.ev;
    }

    @Converter(autoApply = true)
    public static class RoleEnumConverter extends BosEnumConverter<RoleEnum> {}
}