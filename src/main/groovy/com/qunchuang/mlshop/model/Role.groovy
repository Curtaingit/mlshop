package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import groovy.transform.CompileStatic

import javax.persistence.CascadeType;
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany;

/**
 * @author Curtain
 * @date 2018/10/9 14:15
 */

@Entity
@SchemaDocumentation("角色")
@CompileStatic
@Bostype("A09")
public class Role extends BosEntity{

    /**
     * 角色
     */
    String role;

    /**
     * 权限集合
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true,fetch = FetchType.EAGER)
    Set<PrivilegeItem> privilegeItems = new HashSet<>();

}
