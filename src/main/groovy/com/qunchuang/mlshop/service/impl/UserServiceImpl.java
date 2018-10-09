package com.qunchuang.mlshop.service.impl;


import com.qunchuang.mlshop.model.user.User;
import com.qunchuang.mlshop.repo.UserRepository;
import com.qunchuang.mlshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Curtain
 * @date 2018/8/9 8:32
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findByUsername(String username) {
        Optional<User> optional = userRepository.findByUsername(username);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            //new runtimeexception
            return null;
        }
    }


    @Override
    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone).get();
    }


    @Override
    public User findOne(String id) {
        return userRepository.findById(id).get();
    }


}
