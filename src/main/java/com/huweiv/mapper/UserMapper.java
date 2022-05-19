package com.huweiv.mapper;

import com.huweiv.domain.User;
import com.huweiv.domain.UserAndRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HUWEIV
 * @version 1.0.0
 * @ClassName Usermapper
 * @Description TODO
 * @CreateTime 2022/4/10 10:25
 */
public interface UserMapper {

    @Select("select * from sys_user where username=#{username} and password=#{password}")
    User findUserByUsernameAndPassword(User user);

    @Select("select * from sys_user where username=#{username}")
    User findUserByUsername(String username);

    @Select("select * from sys_user where id=#{userId}")
    User findUserById(Long userId);

    @Select("select * from sys_user")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "password", property = "password"),
            @Result(
                    column = "id",
                    property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.huweiv.mapper.RoleMapper.findRoleByUserId")
            )
    })
    List<User> findAll();

    @Insert("insert into sys_user values(#{id}, #{username}, #{email}, #{password}, #{phoneNum})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(User user);

    @Insert("insert into sys_user_role values(#{userId}, #{roleId})")
    void saveUserIdAndRoleId(UserAndRole userAndRole);

    @Delete("delete from sys_user where id=#{userId}")
    void deleteById(long userId);

    @Delete("delete from sys_user_role where userId=#{userId}")
    void deleteUserIdAndRoleIdByUserId(long userId);

    @Select("select * from sys_user where id=#{userId}")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(column = "username", property = "username"),
            @Result(column = "email", property = "email"),
            @Result(column = "password", property = "password"),
            @Result(column = "phoneNum", property = "phoneNum"),
            @Result(
                    column = "id",
                    property = "roles",
                    javaType = List.class,
                    many = @Many(select = "com.huweiv.mapper.RoleMapper.findRoleByUserId")
            )
    })
    User findUserByUserId(long userId);

    @Update("update sys_user set username=#{username}, email=#{email}, password=#{password}, phoneNum=#{phoneNum} where id=#{id}")
    void update(User user);

    @Update("update sys_user set password=#{password} where username=#{username}")
    void updateUserPwdByUsername(User user);

    @Update("update sys_user set password=#{password} where id=#{userId}")
    void updateUserPwdById(User user);

    @Select("select * from sys_user where username=#{username} and phoneNum=#{phoneNum}")
    User findUserByPhoneNum(User user);

    @Update("update sys_user set username=#{username}, email=#{email}, phoneNum=#{phoneNum} where id=#{id}")
    void updateById(User user);

    @Update("update sys_user set email=#{email}, phoneNum=#{phoneNum} where id=#{id}")
    void updateByIdNoUsername(User user);
}
