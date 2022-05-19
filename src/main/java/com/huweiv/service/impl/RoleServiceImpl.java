package com.huweiv.service.impl;

import com.huweiv.domain.Role;
import com.huweiv.mapper.RoleMapper;
import com.huweiv.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleServiceImpl
 * @Description TODO
 * @CreateTime 2022/4/10 10:28
 */
@Service("roleService")
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, readOnly = false)
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> list() {
        return roleMapper.findAll();
    }

    @Override
    public int save(Role role) {
        Role roleInfo = roleMapper.findRoleByRoleName(role.getRoleName());
        if (roleInfo != null)
            return 0;
        roleMapper.save(role);
        return 1;
    }

    @Override
    public void delete(Long roleId) {
        roleMapper.deleteUserIdAndRoleIDByRoleId(roleId);
        roleMapper.deleteById(roleId);
    }

    @Override
    public Role findSingleRole(long roleId) {
        return roleMapper.findRoleByRoleId(roleId);
    }

    @Override
    public int update(Role role, int isUpdateUsername) {
        if (isUpdateUsername == 1) {
            Role roleInfo = roleMapper.findRoleByRoleName(role.getRoleName());
            if (roleInfo != null)
                return 0;
            roleMapper.updateById(role);
        }
        else
            roleMapper.updateByIdNoRoleName(role);
        return 1;
    }
}
