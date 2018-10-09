package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import groovy.transform.CompileStatic;

import javax.persistence.Entity;

/**
 * @author Curtain
 * @date 2018/10/9 14:15
 */

@Entity
@SchemaDocumentation("角色")
@CompileStatic
@Bostype("A09")
public class Role extends BosEntity{

    String role;
}
