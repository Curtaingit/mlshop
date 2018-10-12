package com.qunchuang.mlshop.controller

import com.qunchuang.mlshop.anntations.RoleAuthority
import com.qunchuang.mlshop.enums.RoleAuthorityFunctionEnum
import com.qunchuang.mlshop.graphql.annotation.GRequestMapping
import com.qunchuang.mlshop.graphql.annotation.GRestController
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation
import com.qunchuang.mlshop.model.Administ
import com.qunchuang.mlshop.repo.AdministRepository
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
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

    @Autowired
    private AdministRepository administRepository;

    //测试无权限
    @RequestMapping("/without")
    public String withoutAuthority(){
        throw new BadCredentialsException("未登录");
        return "without";
    }

    //测试 graphql mutation 普通实体返回 + 权限
    @GRequestMapping("/administFindOne")
    @RoleAuthority
    //todo  这里需要换成是  @PreAuthorize 做下测试  看下效果
    public Administ test(@RequestParam("id")String id){
        return administRepository.findById(id).get();
    }

    //测试 graphql mutation 集合实体返回 + 权限
    @GRequestMapping("/administFindAll")
//    @RoleAuthority(RoleAuthorityFunctionEnum.ORDER_LAUNDRY)
    public List<Administ> findAll(@RequestParam(name = "id",required = false)String id){
        return administRepository.findAll();
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
