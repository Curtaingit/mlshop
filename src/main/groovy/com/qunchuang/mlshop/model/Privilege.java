package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity;
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import groovy.transform.CompileStatic;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;

/**
 * @author Curtain
 * @date 2018/10/9 14:24
 */

@Entity
@SchemaDocumentation("权限")
@CompileStatic
@Bostype("A07")
@Getter
@Setter
public class Privilege extends BosEntity implements GrantedAuthority {
    /**
     * 权限
     */
    @SchemaDocumentation("权限")
    private String privilege;

    /**
     * 名称（中文描述）
     */
    @SchemaDocumentation("名称（中文描述）")
    private  String name;

    /**
     * 类别
     */
    @SchemaDocumentation("类别")
    private Integer category;


    @Override
    public String getAuthority() {
        return privilege;
    }

    @Override
    public String toString() {
        return "Privilege{" +
                "privilege='" + privilege + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                '}';
    }
}
