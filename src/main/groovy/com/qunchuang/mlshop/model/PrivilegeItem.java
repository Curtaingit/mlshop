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
 * @date 2018/10/9 14:50
 */

@Entity
@SchemaDocumentation("权限集合")
@Bostype("A08")
@Getter
@Setter
public class PrivilegeItem extends BosEntity {
    /**
     * 权限
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true,fetch = FetchType.EAGER)
    Set<Privilege> privilegeItems = new HashSet<>();
    /**
     * 约束规则
     */
    String constraintRule;

}
