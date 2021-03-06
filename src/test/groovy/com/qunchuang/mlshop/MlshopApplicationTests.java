package com.qunchuang.mlshop;

import com.qunchuang.mlshop.enums.RoleAuthorityFunctionConst;
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
    AdministRepository administRepository;

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
        //

        administ.setName("小明");
        administ.setUsername("admin");
        administ.setPassword("1");
        administ.setTel("13512343423");

        PrivilegeItem privilegeItem1 = new PrivilegeItem();
        PrivilegeItem privilegeItem2 = new PrivilegeItem();
        PrivilegeItem privilegeItem3 = new PrivilegeItem();
        PrivilegeItem privilegeItem4 = new PrivilegeItem();

        Privilege privilege1 = new Privilege();
        privilege1.setPrivilege(RoleAuthorityFunctionConst.ADMIN_MANAGEMENT);
        privilege1.setCategory(0);
        privilege1.setName("用户管理");
        Privilege privilege2 = new Privilege();
        privilege2.setPrivilege(RoleAuthorityFunctionConst.ROLE_MANAGEMENT);
        privilege2.setName("角色管理");
        privilege2.setCategory(0);
        Privilege privilege3 = new Privilege();
        privilege3.setPrivilege(RoleAuthorityFunctionConst.ORDER_MANAGEMENT);
        privilege3.setName("订单管理");
        privilege3.setCategory(1);
        Privilege privilege4 = new Privilege();
        privilege4.setPrivilege(RoleAuthorityFunctionConst.PRODUCT_MANAGEMENT);
        privilege4.setName("商品管理");
        privilege4.setCategory(2);

        privilege1 = privilegeRepository.save(privilege1);
        privilege2 = privilegeRepository.save(privilege2);
        privilege3 = privilegeRepository.save(privilege3);
        privilege4 = privilegeRepository.save(privilege4);

        privilegeItem1.setPrivilege(privilege1);
        privilegeItem2.setPrivilege(privilege2);
        privilegeItem3.setPrivilege(privilege3);
        privilegeItem3.setConstraintRule("Order.amount>500");
        privilegeItem4.setPrivilege(privilege4);


        Role role1 = new Role();
        role1.setName("账号管理员");
        role1.getPrivilegeItems().add(privilegeItem1);
        role1.getPrivilegeItems().add(privilegeItem2);

        Role role2 = new Role();
        role2.setName("订单管理员");
        role2.getPrivilegeItems().add(privilegeItem3);

        Role role3 = new Role();
        role3.setName("商品管理员");
        role3.getPrivilegeItems().add(privilegeItem4);

        role1 = roleRepository.save(role1);
        role2 = roleRepository.save(role2);
        role3 = roleRepository.save(role3);


        RoleItem roleItem1 = new RoleItem();
        RoleItem roleItem2 = new RoleItem();
        RoleItem roleItem3 = new RoleItem();

        roleItem1.setRole(role1);
        roleItem2.setRole(role2);
        roleItem3.setRole(role3);

        administ.getRoleItems().add(roleItem1);
        administ.getRoleItems().add(roleItem2);
        administ.getRoleItems().add(roleItem3);

        //todo 这里如果不前保存 name 和 privilege 会出现以下错误   暂时未找到解决方案
        //todo object references an unsaved transient instance - save the transient instance before flushing

//        List<String> collect = administ.getRoleItems()
//                .stream()
//                .map(roleItem -> roleItem.getRole()).flatMap(role -> role.getPrivilegeItems().stream()).map(PrivilegeItem::getPrivilege).map(privilege -> privilege.getPrivilege()).collect(Collectors.toList());
//
//        administ.setOwnPrivilege(collect.toString());
        administRepository.save(administ);


    }


    @Test
    public void TestSpel() {
/*        //1.首先准备测试数据
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(4);
        collection.add(5);
        Map<String, Object> map = new HashMap<String, Object>();
        Administ administ = new Administ();
        administ.setUsername("aaaaaaaaa");
        User user = new User();
        user.setUsername("uuuuuuuuu");
        map.put("admin", administ);
        map.put("user", user);

        //2.集合或数组测试
//        EvaluationContext context1 = new StandardEvaluationContext();
//        context1.setVariable("collection", collection);
//        Collection<Integer> result1 =
//                parser.parseExpression("#collection.?[#this>4]").getValue(context1, Collection.class);
//        Assert.assertEquals(1, result1.size());
//        Assert.assertEquals(new Integer(5), result1.iterator().next());

        //3.字典测试
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("administ", administ);
        Administ value1 = parser.parseExpression("#administ()").getValue(context2, Administ.class);
        String value = parser.parseExpression("#administ.username").getValue(context2, String.class);

        List<Integer> result3 =
                parser.parseExpression("#map.?[key != 'D'].![value+1]").getValue(context2, List.class);
        Assert.assertEquals(new Integer(3), result3.iterator().next());*/
    }


}
