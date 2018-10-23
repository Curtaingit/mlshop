package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity;
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Curtain
 * @date 2018/10/9 14:15
 */

@Entity
@SchemaDocumentation("角色")
@Bostype("A09")
@Getter
@Setter
public class Role extends BosEntity {

    /**
     * 角色
     */
    String name;

    /**
     * 权限集合
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true,fetch = FetchType.EAGER)
    Set<PrivilegeItem> privilegeItems = new HashSet<>();
}
