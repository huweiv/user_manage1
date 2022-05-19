package com.huweiv.service.impl;

import com.huweiv.domain.User;
import com.huweiv.domain.UserAndRole;
import com.huweiv.mapper.UserMapper;
import com.huweiv.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName UserServiceImpl
 * @Description TODO
 * @CreateTime 2022/4/10 10:28
 */
@Service("userService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Resource(name = "bCryptPasswordEncoder")
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> list() {
        return userMapper.findAll();
    }

    @Override
    public int save(User user, Long[] roleIds) {
        User userInfo = userMapper.findUserByUsername(user.getUsername());
        if (userInfo != null)
            return 3;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.save(user);
        if (roleIds == null)
            return 4;
        for (long roleId : roleIds) {
            UserAndRole userAndRole = new UserAndRole();
            userAndRole.setUserId(user.getId());
            userAndRole.setRoleId(roleId);
            userMapper.saveUserIdAndRoleId(userAndRole);
        }
        return 4;
    }

    @Override
    public void delete(long userId) {
        userMapper.deleteUserIdAndRoleIdByUserId(userId);
        userMapper.deleteById(userId);
    }

    @Override
    public User login(User user) {
        try {
            User userInfo = userMapper.findUserByUsername(user.getUsername());
            if (userInfo == null)
                return null;
            if (bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword()))
                return userInfo;
        } catch (EmptyResultDataAccessException ignored) {
        }
        return null;
    }

    @Override
    public User findSingleUser(long userId) {
        return userMapper.findUserByUserId(userId);
    }

    @Override
    public int editPwd(User user, String editPwd) {
        User userInfo = userMapper.findUserByUsername(user.getUsername());
        if (!bCryptPasswordEncoder.matches(user.getPassword(), userInfo.getPassword()))
            return 1;
        user.setPassword(bCryptPasswordEncoder.encode(editPwd));
        userMapper.updateUserPwdByUsername(user);
        return 2;
    }

    @Override
    public int update(User user, Long[] roleIds, int isUpdateUsername) {
        if (isUpdateUsername == 1) {
            User userInfo = userMapper.findUserByUsername(user.getUsername());
            if (userInfo != null)
                return 2;
            userMapper.updateById(user);
        }
        else
            userMapper.updateByIdNoUsername(user);
        userMapper.deleteUserIdAndRoleIdByUserId(user.getId());
        if (roleIds == null)
            return 3;
        for (Long roleId : roleIds) {
            UserAndRole userAndRole = new UserAndRole();
            userAndRole.setUserId(user.getId());
            userAndRole.setRoleId(roleId);
            userMapper.saveUserIdAndRoleId(userAndRole);
        }
        return 3;
    }

    @Override
    public int register(User user) {
        User userInfo = userMapper.findUserByUsername(user.getUsername());
        if (userInfo != null)
            return 5;
        userMapper.save(user);
        return 6;
    }

    @Override
    public int forgetPwd(User user) {
        User userInfo = userMapper.findUserByPhoneNum(user);
        if (userInfo == null)
            return 3;
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userMapper.updateUserPwdByUsername(user);
        return 4;
    }
}
