package com.example.community.service;

import com.example.community.dao.UserMapper;
import com.example.community.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String,Object> register(User user) throws IllegalAccessException {
        Map<String,Object> map= new HashMap<>();

        if(user==null){
            throw new IllegalAccessException("参数不能为空");
        }
        if(StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg","账号不能为空");
        }
        return  map;

    }


}
