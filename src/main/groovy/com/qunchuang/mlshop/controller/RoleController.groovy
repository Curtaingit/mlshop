package com.qunchuang.mlshop.controller

import com.qunchuang.mlshop.anntations.Exclude
import com.qunchuang.mlshop.anntations.RoleAuthority
import com.qunchuang.mlshop.enums.RoleAuthorityFunctionEnum
import com.qunchuang.mlshop.graphql.annotation.GRequestMapping
import com.qunchuang.mlshop.graphql.annotation.GRestController
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation
import com.qunchuang.mlshop.model.Role
import com.qunchuang.mlshop.service.RoleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
/**
 * @author Curtain
 * @date 2018/10/23 17:01
 */

@SchemaDocumentation("角色")
@GRestController("role")
@RestController
class RoleController {


    @Autowired
    private RoleService roleService;

    @SchemaDocumentation("增加角色信息")
    @GRequestMapping(path = "/add", method = RequestMethod.POST)
    @RoleAuthority(RoleAuthorityFunctionEnum.ROLE_MANAGEMENT)
    Role addRole(@RequestParam(name = "role", required = true) Role role) {
        return roleService.save(role);
    }

    @SchemaDocumentation("修改角色信息")
    @Exclude
    @GRequestMapping(path = "/update", method = RequestMethod.POST)
    @RoleAuthority(RoleAuthorityFunctionEnum.ROLE_MANAGEMENT)
    public Role updateRole(@RequestParam(name = "role", required = true) Role role) {
        return roleService.update(role);
    }

}
