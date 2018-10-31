package com.qunchuang.mlshop.controller

import com.qunchuang.mlshop.anntations.Exclude
import com.qunchuang.mlshop.anntations.RoleAuthority
import com.qunchuang.mlshop.enums.RoleAuthorityFunctionEnum
import com.qunchuang.mlshop.graphql.annotation.GRequestMapping
import com.qunchuang.mlshop.graphql.annotation.GRestController
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation
import com.qunchuang.mlshop.model.Administ
import com.qunchuang.mlshop.service.AdministService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
/**
 * @author Curtain
 * @date 2018/8/10 9:00
 */

@SchemaDocumentation("管理员")
@GRestController("administ")
@RestController
public class AdministController {

    @Autowired
    private AdministService administService;

    @SchemaDocumentation("增加管理员信息")
    @GRequestMapping(path = "/add", method = RequestMethod.POST)
//    @PreAuthorize("hasAuthority('ADMIN_MANAGEMENT')")
    Administ addAdminist(
            @RequestParam(name = "administ", required = true) Administ administ) {
        return administService.save(administ);
    }

    @SchemaDocumentation("修改管理员信息")
    @Exclude
    @GRequestMapping(path = "/update", method = RequestMethod.POST)
    @RoleAuthority(RoleAuthorityFunctionEnum.ADMIN_MANAGEMENT)
    Administ updateAdminist(
            @RequestParam(name = "administ", required = true) Administ administ) {
        return administService.update(administ);
    }

}
