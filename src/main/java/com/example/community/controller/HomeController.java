package com.example.community.controller;

import com.example.community.entity.DiscussPost;
import com.example.community.entity.Page;
import com.example.community.entity.User;
import com.example.community.service.DiscusspostService;
import com.example.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/16 09:16
 */
@Controller
public class HomeController {

    @Autowired
    UserService userService;
    @Autowired
    DiscusspostService discusspostService;


    @RequestMapping("/index")
    public  String pagemain(Model model, Page page){
        page.setRows(discusspostService.findDiscussrows(0));
        page.setPath("/index");

        List<DiscussPost> list=discusspostService.findDiscussPost(0,0,10);
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if(list!=null){
            for(DiscussPost post:list){
                Map<String,Object> map=new HashMap<>();
                map.put("post",post);
                User user= userService.findUserById(post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return  "/index";
    }

}
