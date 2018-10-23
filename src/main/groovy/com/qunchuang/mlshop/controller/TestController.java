package com.qunchuang.mlshop.controller;

import com.qunchuang.mlshop.anntations.RoleAuthority;
import com.qunchuang.mlshop.graphql.annotation.GRequestMapping;
import com.qunchuang.mlshop.graphql.annotation.GRestController;
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation;
import com.qunchuang.mlshop.model.Administ;
import com.qunchuang.mlshop.repo.AdministRepository;
import com.validator.bos.anntations.DomainRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.List;

import static com.qunchuang.mlshop.enums.RoleAuthorityFunctionConst.*;

/**
 * @author Curtain
 * @date 2018/8/15 15:14
 */

@SchemaDocumentation("用户信息")
@GRestController("mlshop")
@RestController
@Validated
class TestController {


    @Autowired
    private AdministRepository administRepository;

    @RequestMapping("/test1")
    public String valid(@Min(value = 11) Integer name) {
        return "Ss";
    }


    //测试无权限
    @RequestMapping("/without")
    public String withoutAuthority(){
//        throw new BadCredentialsException("未登录");
        return "without";
    }

    //测试 graphql mutation 普通实体返回 + 权限
    @GRequestMapping("/administFindOne")
    @RoleAuthority
    public Administ test(@RequestParam("id")String id){
        return administRepository.findById(id).get();
    }

    //测试 graphql mutation 集合实体返回 + 权限
    @GRequestMapping("/administFindAll")
    //todo 修改value  实现用枚举的形式
    @PreAuthorize(NO_ROLE)
    public List<Administ> findAll(@RequestParam(name = "id",required = false)String id){
        return administRepository.findAll();

    }

    @GRequestMapping("/addAdminist")
    @PreAuthorize(PLATFORM_TEXT)
    public Administ addUser(@DomainRule(value = "tel && name") @RequestParam("administ") Administ administ){
       return administ;
    }


    //测试默认的登录权限
    @RequestMapping("/default")
    @PreAuthorize(ORDER)
    public String defaultAuthority(){
        return "default";
    }


    //测试指定权限
    @RequestMapping("/indicate_a1")
    public String indicateAuthoritySuccess(){
        return "indicate";
    }

    //测试指定权限 失败
    @RequestMapping("/indicate_e4")
    public String indicateAuthorityFail(){
        return "indicate";
    }

}
