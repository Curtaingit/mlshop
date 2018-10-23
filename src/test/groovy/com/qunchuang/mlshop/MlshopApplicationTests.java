package com.qunchuang.mlshop;

import com.qunchuang.mlshop.enums.RoleAuthorityFunctionConst;
import com.qunchuang.mlshop.model.*;
import com.qunchuang.mlshop.repo.AdministRepository;
import com.qunchuang.mlshop.repo.PrivilegeItemRepository;
import com.qunchuang.mlshop.repo.RoleItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MlshopApplicationTests {

    @Autowired
    private AdministRepository administRepository;

    @Autowired
    RoleItemRepository roleItemRepository;

    @Autowired
    PrivilegeItemRepository privilegeItemRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void saveAdminist() {

        Administ administ = new Administ();

        administ.setName("小明");
        administ.setUsername("admin");
        administ.setPassword("1");
        administ.setTel("135****3423");

        PrivilegeItem privilegeItem = new PrivilegeItem();

        Privilege privilege = new Privilege();
        privilege.setPrivilege(RoleAuthorityFunctionConst.NO_ROLE);
        Privilege privilege2 = new Privilege();
        privilege2.setPrivilege(RoleAuthorityFunctionConst.ORDER);

        privilegeItem.getPrivilegeItems().add(privilege);
        privilegeItem.getPrivilegeItems().add(privilege2);

        privilegeItem = privilegeItemRepository.save(privilegeItem);

        Role role = new Role();
        role.setRole("Role_Admin");
        role.setPrivilegeItem(privilegeItem);

        RoleItem roleItem = new RoleItem();
        roleItem.getRoleItems().add(role);
        roleItem = roleItemRepository.save(roleItem);


        administ.setRoleItem(roleItem);

        //todo 这里如果不前保存 role 和 privilege 会出现以下错误   暂时未找到解决方案
        //todo object references an unsaved transient instance - save the transient instance before flushing

        administRepository.save(administ);


    }

}
