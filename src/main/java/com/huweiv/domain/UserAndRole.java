package com.huweiv.domain;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName UserAndRole
 * @Description TODO
 * @CreateTime 2022/4/10 13:54
 */
public class UserAndRole {

    private Long userId;
    private Long roleId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
