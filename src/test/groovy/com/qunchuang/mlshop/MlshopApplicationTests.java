package com.qunchuang.mlshop;

import com.qunchuang.mlshop.model.*;
import com.qunchuang.mlshop.repo.AdministRepository;
import com.qunchuang.mlshop.repo.PrivilegeRepository;
import com.qunchuang.mlshop.repo.RoleRepository;
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
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

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

        RoleItem roleItem = new RoleItem();
        PrivilegeItem privilegeItem = new PrivilegeItem();

        Privilege privilege = new Privilege();
        privilege.setPrivilege("A1,A2");
        privilege = privilegeRepository.save(privilege);

        Role role = new Role();
        role.setRole("Role_Admin");
        role = roleRepository.save(role);

        roleItem.setRole(role);
        privilegeItem.setPrivilege(privilege);

        administ.getPrivilegeItems().add(privilegeItem);
        administ.getRoleItems().add(roleItem);

        //todo 这里如果不前保存 role 和 privilege 会出现以下错误   暂时未找到解决方案
        //todo object references an unsaved transient instance - save the transient instance before flushing

        administRepository.save(administ);


    }

}
