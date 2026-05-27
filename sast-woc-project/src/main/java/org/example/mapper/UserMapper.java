package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.User;
@Mapper
public interface UserMapper{
    @Select("select id,user_code, password, name, academy_id, role, com_id from user where user_code=#{userCode}")
    User selectByUserCode(String userCode);
    @Insert("insert into user(user_code, password, name, academy_id, role, com_id) " +
            "values (#{userCode}, #{password}, #{name}, #{academyId}, #{role}, #{comId})")
    void insert(User user);
    @Delete("delete from user where user_code = #{userCode}")
    void deleteByUserCode(String userCode);
}