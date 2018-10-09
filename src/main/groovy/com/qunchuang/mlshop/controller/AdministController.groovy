package com.qunchuang.mlshop.controller

import com.qunchuang.mlshop.graphql.annotation.GRestController
import com.qunchuang.mlshop.graphql.annotation.SchemaDocumentation
import com.qunchuang.mlshop.service.AdministService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RestController
/**
 * @author Curtain
 * @date 2018/8/10 9:00
 */

@SchemaDocumentation("管理员")
@GRestController("mlshop")
@RestController
@CompileStatic
public class AdministController {

    @Autowired
    private AdministService administService;

//    @SchemaDocumentation("增加管理员信息")
//    @GRequestMapping(path = "/addadminist", method = RequestMethod.POST)
//    Administ addAdminist(
//            @RequestParam(name = "administ", required = true) Administ administ) {
//        return administService.save(administ);
//    }
//
//    @SchemaDocumentation("修改管理员信息")
//    @Exclude
//    @GRequestMapping(path = "/updateadminist", method = RequestMethod.POST)
//    Administ updateAdminist(
//            @RequestParam(name = "administ", required = true) Administ administ) {
//        return administService.update(administ);
//    }
//
//    @SchemaDocumentation("修改管理员密码")
//    @Exclude
//    @GRequestMapping(path = "/administmodifypassword", method = RequestMethod.POST)
//    Administ modifyPassword(
//            @RequestParam(name = "administ", required = true) Administ administ) {
//        return administService.modifyPassword(administ);
//    }
}
