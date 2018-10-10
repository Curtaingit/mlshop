package com.qunchuang.mlshop.model

import com.bos.domain.Bostype
import com.bos.domain.Entry
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation
import groovy.transform.CompileStatic

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.ManyToOne
/**
 * @author Curtain
 * @date 2018/10/9 14:50
 */

@Entity
@SchemaDocumentation("权限分录")
@CompileStatic
@Bostype("A08")

class PrivilegeItem extends Entry {
    @ManyToOne(fetch = FetchType.EAGER)
    Privilege privilege;

}
