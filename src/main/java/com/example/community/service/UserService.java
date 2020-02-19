package com.example.community.service;

import com.example.community.dao.LoginTicketMapper;
import com.example.community.dao.UserMapper;
import com.example.community.entity.LoginTicket;
import com.example.community.entity.User;
import com.example.community.util.CommunityConstant;
import com.example.community.util.CommunityUtil;
import com.example.community.util.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/17 14:23
 */
@Service
public class UserService implements CommunityConstant {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private MailClient mailClient;

    @Value("http://localhost:8080")
    private String domain;

    @Autowired
    LoginTicketMapper loginTicketMapper;

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
        if(StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg","密码不能为空");
        }
        if(StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg","邮箱不能为空");
        }

        User u=userMapper.selectByName(user.getUsername());
        if(u!=null){
            map.put("usernameMsg","该账号已存在");
            return  map;
        }
        u=userMapper.selectByEmail(user.getEmail());
        if(u!=null){
            map.put("emailMsg","该邮箱已被注册");
            return  map;
        }

        user.setSalt(CommunityUtil.generateUUID());
        user.setPassword(CommunityUtil.md5(user.getPassword()+user.getSalt()));
        user.setType(0);
        user.setStatus(0);
        user.setActivationCode(CommunityUtil.generateUUID());
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        //激活邮件
        Context context = new Context();
        context.setVariable("email",user.getEmail());
        // http://localhost:8080/activation/101/code
        String url=domain+"/activation/"+user.getId()+"/"+user.getActivationCode();
        context.setVariable("url",url);
        String content=templateEngine.process("/mail/activation",context);
        mailClient.sendMail(user.getEmail(),"激活账号",content);

        return  map;

    }

    public  int activation(int userId,String code){
        User user=userMapper.selectById(userId);
        if(user.getStatus()==1)
            return ACTIVATION_REPEAT;
        else if(user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId,1);
            return ACTIVATION_SUCCESS;
        }else {
            return  ACTIVATION_FAILURE;
        }
    }

    public  Map<String,Object> login(String username,String password,int livetime){
        Map<String,Object> map=new HashMap<>();
        //空值处理
        if(StringUtils.isBlank(username)){
            map.put("usernameMsg","用户名不能为空");
            return  map;
        }
        if(StringUtils.isBlank(password)){
            map.put("usernameMsg","用户密码不能为空");
            return  map;
        }
        //验证账号
        User user=userMapper.selectByName(username);
        if(user==null){
            map.put("usernameMsg","该账号不存在");
            return  map;
        }
        //验证状态
//        if(user.getStatus()==0){
//            map.put("usernameMsg","该账号未激活");
//            return  map;
//        }
        // 验证密码
        password = CommunityUtil.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确!");
            return map;
        }
        //生成登录凭证
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setUserId(user.getId());
        loginTicket.setTicket(CommunityUtil.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + livetime * 1000));
        loginTicketMapper.insertLoginTicket(loginTicket);
        map.put("ticket",loginTicket.getTicket());
        return  map;
    }
    public void logout(String ticket) {
        loginTicketMapper.updateStatus(ticket, 1);
    }


}
