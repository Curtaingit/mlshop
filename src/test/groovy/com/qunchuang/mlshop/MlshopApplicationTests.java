package com.qunchuang.mlshop;

import com.qunchuang.mlshop.enums.RoleAuthorityFunctionConst;
import com.qunchuang.mlshop.model.*;
import com.qunchuang.mlshop.repo.AdministRepository;
import com.qunchuang.mlshop.repo.PrivilegeRepository;
import com.qunchuang.mlshop.repo.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static com.validator.bos.core.RuleParser.parser;

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

        administRepository.save(administ);


    }


    @Test
    public void TestSpel(){
        //1.首先准备测试数据
        Collection<Integer> collection = new ArrayList<Integer>();
        collection.add(4);   collection.add(5);
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("a", 1);    map.put("b", 2);

        //2.集合或数组测试
        EvaluationContext context1 = new StandardEvaluationContext();
        context1.setVariable("collection", collection);
        Collection<Integer> result1 =
                parser.parseExpression("#collection.?[#this>4]").getValue(context1, Collection.class);
        Assert.assertEquals(1, result1.size());
        Assert.assertEquals(new Integer(5), result1.iterator().next());

        //3.字典测试
        EvaluationContext context2 = new StandardEvaluationContext();
        context2.setVariable("map", map);
        Map<String, Integer> result2 =
                parser.parseExpression("#map.?[#this.key != 'a']").getValue(context2, Map.class);
        Assert.assertEquals(1, result2.size());

        List<Integer> result3 =
                parser.parseExpression("#map.?[key != 'D'].![value+1]").getValue(context2, List.class);
        Assert.assertEquals(new Integer(3), result3.iterator().next());
    }



}
