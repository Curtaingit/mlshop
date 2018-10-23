package com.qunchuang.mlshop.service;

import com.qunchuang.mlshop.model.Role;

/**
 * @author Curtain
 * @date 2018/10/23 16:00
 */
public interface RoleService {


    /**
     * 新增
     * @param role
     * @return
     */
    Role save(Role role);

    /**
     * 修改
     * @param role
     * @return
     */
    Role update(Role role);
}
