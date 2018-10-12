package com.qunchuang.mlshop.model;

import com.bos.domain.BosEntity
import com.bos.domain.Bostype;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import groovy.transform.CompileStatic;

import javax.persistence.Entity;

/**
 * @author Curtain
 * @date 2018/10/9 14:24
 */

@Entity
@SchemaDocumentation("权限")
@CompileStatic
@Bostype("A07")
public class Privilege extends BosEntity {
    String privilege;


}
