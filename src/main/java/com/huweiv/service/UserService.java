package com.huweiv.service;

import com.huweiv.domain.User;

import java.util.List;

public interface UserService {

    List<User> list();
    int save(User user, Long[] roleIds);
    void delete(long userId);
    User login(User user);
    User findSingleUser(long userId);
    int update(User user, Long[] roleIds, int isUpdateUsername);
    int register(User user);
    int editPwd(User user, String editPwd);
    int forgetPwd(User user);

}
