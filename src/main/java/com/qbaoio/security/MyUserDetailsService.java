package com.qbaoio.security;

import com.qbaoio.qbaoio.dao.user.UserDao;
import com.qbaoio.qbaoio.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hepen on 7/12/2017.
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(name)) {
            throw new UsernameNotFoundException("User name is empty");
        }

        List<User> userList = userDao.findByUserName(name);
        if (userList == null || userList.size() == 0) {
            throw new UsernameNotFoundException("No user found by name " + name);
        } else if (userList.size() > 1) {
            throw new UsernameNotFoundException("Multi user found by name " + name);
        }

        User user = userList.get(0);
        Set<GrantedAuthority> authorities = new HashSet<>();

        return new org.springframework.security.core.userdetails.User(
                name, "",true,true,true,true,
                authorities);
    }
}
