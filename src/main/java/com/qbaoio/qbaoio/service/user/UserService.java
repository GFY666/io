package com.qbaoio.qbaoio.service.user;

import com.qbaoio.qbaoio.entity.user.User;


public interface UserService {

    String saveUser(User user);

    String activateUser(long id);

    String findByUserName(String userName);

    String findByEmail(String email);

    User findUserNameAndpassword(User user);

    User findEamilAndpassword(User user);
}
