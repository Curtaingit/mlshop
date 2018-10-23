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
        administ.setTel("135****3423");

        PrivilegeItem privilegeItem1 = new PrivilegeItem();
        PrivilegeItem privilegeItem2 = new PrivilegeItem();

        Privilege privilege1 = new Privilege();
        privilege1.setPrivilege(RoleAuthorityFunctionConst.NO_ROLE);
        Privilege privilege2 = new Privilege();
        privilege2.setPrivilege(RoleAuthorityFunctionConst.PLATFORM_TEXT);

        privilege1 = privilegeRepository.save(privilege1);
        privilege2 = privilegeRepository.save(privilege2);

        privilegeItem1.setPrivilege(privilege1);
        privilegeItem1.setConstraintRule("Order.amount>500");
        privilegeItem2.setPrivilege(privilege2);


        Role role1 = new Role();
        role1.setName("Role_Admin");
        role1.getPrivilegeItems().add(privilegeItem1);

        Role role2 = new Role();
        role2.setName("Role_Admin");
        role2.getPrivilegeItems().add(privilegeItem2);

        role1 = roleRepository.save(role1);
        role2 = roleRepository.save(role2);


        RoleItem roleItem1 = new RoleItem();
        RoleItem roleItem2 = new RoleItem();

        roleItem1.setRole(role1);
        roleItem2.setRole(role2);

        administ.getRoleItems().add(roleItem1);
        administ.getRoleItems().add(roleItem2);

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
