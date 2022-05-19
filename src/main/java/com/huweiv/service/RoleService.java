package com.huweiv.service;

import com.huweiv.domain.Role;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleService
 * @Description TODO
 * @CreateTime 2022/4/10 10:25
 */
public interface RoleService {

    List<Role> list();
    int save(Role role);
    void delete(Long roleId);
    Role findSingleRole(long roleId);
    int update(Role role, int isUpdateUsername);
}
