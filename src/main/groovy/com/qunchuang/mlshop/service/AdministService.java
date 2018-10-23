package com.qunchuang.mlshop.service;


import com.qunchuang.mlshop.model.Administ;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Curtain
 * @date 2018/8/9 14:44
 */
public interface AdministService extends UserDetailsService {

    /**
     * 修改
     * @param administ
     * @return
     */
    Administ update(Administ administ);

    /**
     * 创建
     * @param administ
     * @return
     */
    Administ save(Administ administ);

}
