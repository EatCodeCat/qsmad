package com.qsm.ad.services;

import com.qsm.ad.entitys.User;
import com.qsm.ad.repositroys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by think on 2017/8/11.
 */
@Service("userService")
public class UserService extends CrudService<User> implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public void setRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        super.setRepository(userRepository);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findByUsername(s);
    }
}
