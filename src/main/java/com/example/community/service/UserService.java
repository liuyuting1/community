package com.example.community.service;

import com.example.community.dao.UserMapper;
import com.example.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/17 14:23
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserById(int id){
       return  userMapper.selectById(id);
    }
}
