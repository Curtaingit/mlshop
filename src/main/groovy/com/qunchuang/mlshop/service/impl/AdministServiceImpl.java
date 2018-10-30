package com.qunchuang.mlshop.service.impl;


import com.qunchuang.mlshop.exception.MLShopRunTimeException;
import com.qunchuang.mlshop.model.Administ;
import com.qunchuang.mlshop.model.Privilege;
import com.qunchuang.mlshop.model.PrivilegeItem;
import com.qunchuang.mlshop.repo.AdministRepository;
import com.qunchuang.mlshop.service.AdministService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Curtain
 * @date 2018/8/9 14:48
 */

@Service
public class AdministServiceImpl implements AdministService {

    @Autowired
    private AdministRepository administRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Administ> optional = administRepository.findByUsername(Optional.ofNullable(s).orElse(""));
        if (!optional.isPresent()) {
            throw new MLShopRunTimeException("用户不存在，请重新确认你的账号名是否正确");
        }
        return optional.get();
    }

    @Override
    @Transactional
    public Administ update(Administ administ) {
        //todo  超级管理员admin的权限和角色不允许修改

        administ = administRepository.save(administ);
        privilegeCheck(administ);

        return administ;
    }


    @Override
    @Transactional
    public Administ save(Administ administ) {
        administ = administRepository.save(administ);
        privilegeCheck(administ);

        return administ;

    }


    private void privilegeCheck(Administ administ) {
        Administ principal = (Administ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Set<Privilege> principalPrivilege = principal.getRoleItems()
                .stream()
                .flatMap(roleItem -> roleItem.getRole().getPrivilegeItems().stream())
                .map(PrivilegeItem::getPrivilege).collect(Collectors.toSet());

        Set<Privilege> administerPrivilege = administ.getRoleItems()
                .stream()
                .flatMap(roleItem -> roleItem.getRole().getPrivilegeItems().stream())
                .map(PrivilegeItem::getPrivilege).collect(Collectors.toSet());

        if (!principalPrivilege.containsAll(administerPrivilege)) {
            throw new AccessDeniedException("权限添加错误，赋予了本身不具备的权限");
        }
    }

}
