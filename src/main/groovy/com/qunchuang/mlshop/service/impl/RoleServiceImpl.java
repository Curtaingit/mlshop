package com.qunchuang.mlshop.service.impl;

import com.qunchuang.mlshop.model.Role;
import com.qunchuang.mlshop.repo.RoleRepository;
import com.qunchuang.mlshop.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Curtain
 * @date 2018/10/23 16:01
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role update(Role role) {
        return roleRepository.save(role);
    }
}
