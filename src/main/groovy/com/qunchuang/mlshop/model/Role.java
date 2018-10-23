package com.qunchuang.mlshop.model;

import com.bos.domain.Bostype;
import com.bos.domain.Entry;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author Curtain
 * @date 2018/10/9 14:15
 */

@Entity
@SchemaDocumentation("角色")
@Bostype("A09")
@Getter
@Setter
public class Role extends Entry{

    /**
     * 角色
     */
    String role;

    /**
     * 权限集合
     */
    @ManyToOne
    PrivilegeItem privilegeItem;
}
