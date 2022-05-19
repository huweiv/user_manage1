package com.huweiv.mapper;

import com.huweiv.domain.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName RoleMapper
 * @Description TODO
 * @CreateTime 2022/4/10 10:25
 */
public interface RoleMapper {

    @Select("select * from sys_role")
    List<Role> findAll();

    @Insert("insert into sys_role values(#{id}, #{roleName}, #{roleDesc})")
    void save(Role role);

    @Delete("delete from sys_role where id=#{roleId}")
    void deleteById(long roleId);

    @Delete("delete from sys_user_role where roleId=#{roleId}")
    void deleteUserIdAndRoleIDByRoleId(long roleId);

    @Select("select * from sys_user_role ur, sys_role r where ur.userId=#{userId} and ur.roleId=r.id")
    List<Role> findRoleByUserId(long userId);

    @Select("select * from sys_role where id=#{roleId}")
    Role findRoleByRoleId(long roleId);

    @Update("update sys_role set roleName=#{roleName}, roleDesc=#{roleDesc} where id=#{id}")
    void updateById(Role role);

    @Update("update sys_role set roleDesc=#{roleDesc} where id=#{id}")
    void updateByIdNoRoleName(Role role);

    @Select("select * from sys_role where roleName=#{roleName}")
    Role findRoleByRoleName(String roleName);

}
