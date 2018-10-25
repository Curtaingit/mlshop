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

    /*权限集合判断 是否包含*/
    public static boolean authorityJudge(List<String> aggregateList, String subset) {

        List subsetList = Arrays.asList(subset.split(","));

        return aggregateList.containsAll(subsetList);
    }

    /*核对方法权限*/
    public static void checkRoleFunction(Administ administ, String requisite) {


        //如果权限不符合 那么抛出AccessDeniedException  返回403
        List<String> privileges = administ.getRoleItems().stream().flatMap(roleItem -> roleItem.getRole().getPrivilegeItems().stream())
                .map(privilegeItem -> privilegeItem.getPrivilege().getPrivilege()).collect(Collectors.toList());

        if (!authorityJudge(privileges, requisite)) {
            throw new AccessDeniedException("权限不足");
        }

    }
}
