package com.qunchuang.mlshop.model.user

import com.bos.domain.BosEntity
import com.bos.domain.Bostype
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation
import groovy.transform.CompileStatic

import javax.persistence.Entity

@Entity
@SchemaDocumentation("用户")
@CompileStatic
@Bostype("C03")
//默认匿名用户可以访问/默认登陆用户可以访问，默认所有用户禁止访问
//context(x,访问自己的订单)
//@QueryDeny(exceptPredicate=QueryPermission.permission)
//@QueryAllow(exceptPredicate="",exceptAttrs="{}")
class User extends BosEntity {

    @SchemaDocumentation("用户名")
    String username;

    @SchemaDocumentation("密码")
    String password;

    @SchemaDocumentation("注册手机号")
    String registerPhone;

    @SchemaDocumentation("在用手机号")
    String phone;


    @SchemaDocumentation("头像")
    String headImg;

    @SchemaDocumentation("昵称")
    String nickname;
}








