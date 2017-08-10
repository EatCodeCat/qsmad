package com.qsm.ad;

import com.qsm.ad.entitys.User;
import com.qsm.ad.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by think on 2017/8/11.
 */
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //数据库服务类
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户

        List<User> user = userService.findByUsername(username);
        if(user == null && user.size() == 0){
            throw new UsernameNotFoundException("用户名不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.get(0).getUsername(),
                user.get(0).getPassword(), authorities);
    }
}
