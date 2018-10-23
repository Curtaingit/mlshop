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
    String privilege;

    /**
     * 名称（中文描述）
     */
    String name;

    /**
     * 类别
     */
    Integer category;


    @Override
    public String getAuthority() {
        return privilege;
    }
}
