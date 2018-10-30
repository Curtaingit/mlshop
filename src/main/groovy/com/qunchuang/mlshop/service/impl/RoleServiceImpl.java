package com.qunchuang.mlshop.service.impl;

import com.qunchuang.mlshop.model.Administ;
import com.qunchuang.mlshop.model.Privilege;
import com.qunchuang.mlshop.model.PrivilegeItem;
import com.qunchuang.mlshop.model.Role;
import com.qunchuang.mlshop.repo.RoleRepository;
import com.qunchuang.mlshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Curtain
 * @date 2018/10/23 16:01
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Role save(Role role) {

        //todo 需要校验constraint 是否符合规则 能否转化成qfilter  失败则抛出异常

        role = roleRepository.save(role);
        privilegeCheck(role);

        return role;
    }

    @Override
    @Transactional
    public Role update(Role role) {
        //todo 需要校验constraint 是否符合规则 能否转化成qfilter  失败则抛出异常
        role = roleRepository.save(role);
        privilegeCheck(role);
        return role;
    }

    private void privilegeCheck(Role role) {
        Administ principal = (Administ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<Privilege> principalPrivilege = principal.getRoleItems()
                .stream()
                .flatMap(roleItem -> roleItem.getRole().getPrivilegeItems().stream())
                .map(PrivilegeItem::getPrivilege).collect(Collectors.toSet());

        Set<Privilege> rolePrivilege = role.getPrivilegeItems().stream()
                .map(PrivilegeItem::getPrivilege).collect(Collectors.toSet());

        if (!principalPrivilege.containsAll(rolePrivilege)) {
            throw new AccessDeniedException("角色添加错误，赋予了本身不具备的权限");
        }
    }

}
