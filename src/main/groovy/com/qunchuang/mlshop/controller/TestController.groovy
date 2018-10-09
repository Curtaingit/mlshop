package com.qunchuang.mlshop.controller

import com.qunchuang.mlshop.anntations.RoleAuthority
import com.qunchuang.mlshop.enums.RoleAuthorityFunctionEnum
import com.qunchuang.mlshop.graphql.annotation.GRestController
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation
import groovy.transform.CompileStatic
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
/**
 * @author Curtain
 * @date 2018/8/15 15:14
 */

@SchemaDocumentation("用户信息")
@GRestController("mlshop")
@RestController
@CompileStatic
class TestController {

    //测试无权限
    @RequestMapping("/without")
    public String withoutAuthority(){
        return "without";
    }


    //测试默认的登录权限
    @RequestMapping("/default")
    @RoleAuthority
    public String defaultAuthority(){
        return "default";
    }


    //测试指定权限
    @RequestMapping("/indicate_a1")
    @RoleAuthority(RoleAuthorityFunctionEnum.ORDER_LAUNDRY)
    public String indicateAuthoritySuccess(){
        return "indicate";
    }

    //测试指定权限 失败
    @RequestMapping("/indicate_e4")
    @RoleAuthority(RoleAuthorityFunctionEnum.PLATFORM_TEXT)
    public String indicateAuthorityFail(){
        return "indicate";
    }

}
