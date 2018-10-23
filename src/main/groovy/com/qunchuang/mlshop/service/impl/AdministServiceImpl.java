package com.qunchuang.mlshop.service.impl;


import com.qunchuang.mlshop.exception.MLShopRunTimeException;
import com.qunchuang.mlshop.model.Administ;
import com.qunchuang.mlshop.repo.AdministRepository;
import com.qunchuang.mlshop.service.AdministService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public Administ update(Administ administ) {
        //todo  首先在方法上需要功能权限   其次在这里需要做判断  只能赋予 自己子集的权限（角色）    创建也是一样的
        return administRepository.save(administ);
    }

    @Override
    public Administ save(Administ administ) {
        return administRepository.save(administ);
    }

}
