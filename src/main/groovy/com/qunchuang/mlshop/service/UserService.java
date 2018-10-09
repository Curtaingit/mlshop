package com.qunchuang.mlshop.service;


import com.qunchuang.mlshop.model.user.User;

/**
 * @author Curtain
 * @date 2018/8/7 11:21
 */
public interface UserService {

    /**
     * 通过用户名查找
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查找用户
     * @param id
     * @return
     */
    User findOne(String id);

    /**
     * 通过手机号查找
     * @param phone
     * @return
     */
    User findByPhone(String phone);

}
