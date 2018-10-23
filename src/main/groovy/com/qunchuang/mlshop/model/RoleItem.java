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
 * @date 2018/10/22 16:54
 */
@Entity
@SchemaDocumentation("角色集合")
@Bostype("A10")
@Getter
@Setter
public class RoleItem extends Entry{
    /**
     * 角色
     */
    @ManyToOne
    Role role;

}
