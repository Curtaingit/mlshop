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
 * @date 2018/10/9 14:50
 */

@Entity
@SchemaDocumentation("权限集合")
@Bostype("A08")
@Getter
@Setter
public class PrivilegeItem extends Entry {
    /**
     * 权限
     */
    @ManyToOne
    Privilege privilege;
    /**
     * 约束规则
     */
    String constraintRule;

}
