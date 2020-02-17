package com.example.community.dao;

import com.example.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/16 21:04
 */
@Mapper
public interface UserMapper {

    User selectById(int id);
    User selectByName(String username);
    User selectByEmail(String email);

    int insertUser(User user);
    int updateStatus(int id,int status);
    int updateHeader(int id,String  headerUrl);
    int updatePassword(int id,String password);

}
