package com.qsm.ad.services;

import com.qsm.ad.entitys.User;
import com.qsm.ad.repositroys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by think on 2017/8/11.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }


}
