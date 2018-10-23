package com.qunchuang.mlshop.utils;

import com.qunchuang.mlshop.model.Administ;
import org.springframework.security.access.AccessDeniedException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Curtain
 * @date 2018/5/15 16:57
 */
public class RoleUtil {

    /*总部系统管理员权限*/
    public final static String ADMIN_ROLE = "A1,A2,A3,A4,A5,B1,B2,B3,C1,C2,C3,C4,C5,D1,D2,D3,E1,E2,E3,E4,E5";

    /*代理商权限*/
    public final static String AGENT_ROLE = "A1,A2,A3,A5,D2,E6";

    /*门店权限*/
    public final static String STORE_ROLE = "A1,A2,A3,A4";

    /*总部普通管理员*/
    public final static String GENERAL_HQ_ROLE = "A1,A2,A3,A4,A5,B1,B2,B3,C1,C2,C3,C4,C5,D1,D2,D3,E1,E2,E3,E4,E5";

    /*代理商管理员*/
    public final static String GENERAL_AGENT_ROLE = "A1,A2,A3,A5,D2";

    /*权限集合判断 是否包含*/
    public static boolean authorityJudge(String aggregate, String subset) {

        List aggregateList = Arrays.asList(aggregate.split(","));
        List subsetList = Arrays.asList(subset.split(","));

        return aggregateList.containsAll(subsetList);
    }

    /*核对方法权限*/
    public static void checkRoleFunction(Administ administ, String requisite) {


        //如果权限不符合 那么抛出AccessDeniedException  返回403
        List<String> privileges = administ.getRoleItems().stream().flatMap(roleItem -> roleItem.getRole().getPrivilegeItems().stream())
                .map(privilegeItem -> privilegeItem.getPrivilege().getPrivilege()).collect(Collectors.toList());

        if (!authorityJudge(privileges.toString(), requisite)) {
            throw new AccessDeniedException("权限不足");
        }

    }
}
