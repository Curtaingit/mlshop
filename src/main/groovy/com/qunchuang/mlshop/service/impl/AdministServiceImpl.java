package com.qunchuang.mlshop.service.impl;


import com.qunchuang.mlshop.exception.MLShopRunTimeException;
import com.qunchuang.mlshop.model.Administ;
import com.qunchuang.mlshop.repo.AdministRepository;
import com.qunchuang.mlshop.service.AdministService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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

        //只有总管理员才能修改普通管理员
        Administ admin = (Administ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Administ result =  administRepository.findById(administ.getId()).get();

        if (result.getPassword()==null){
            administ.setPassword(null);
        }else {
            administ.setPassword(result.getPassword());
        }
        return administRepository.save(administ);
    }

}
