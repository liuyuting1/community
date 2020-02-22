package com.example.community.controller;

import com.example.community.entity.Comment;
import com.example.community.entity.DiscussPost;
import com.example.community.entity.Page;
import com.example.community.entity.User;
import com.example.community.service.CommentService;
import com.example.community.service.DiscusspostService;
import com.example.community.service.UserService;
import com.example.community.util.CommunityConstant;
import com.example.community.util.CommunityUtil;
import com.example.community.util.HostHolder;
import com.example.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.Path;
import java.util.*;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/22 10:54
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController implements CommunityConstant {

    @Autowired
   private DiscusspostService discusspostService;
    @Autowired
    SensitiveFilter sensitiveFilter;
    @Autowired
   private CommentService commentService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;

    @RequestMapping(path = "/ajax",method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content) {
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSonString(403, "你还没有登录哦!");
        }

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        discusspostService.addDiscussPost(post);

        // 报错的情况,将来统一处理.
        return CommunityUtil.getJSonString(0, "发布成功!");
    }


    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String getDiscussPost(@PathVariable("discussPostId") int discussPostId, Model model) {
        // 帖子
        DiscussPost post = discusspostService.findDiscussPostById(discussPostId);
        model.addAttribute("post", post);
        // 作者
        User user = userService.findUserById(post.getUserId());
        model.addAttribute("user", user);

        return "/site/discuss-detail";

    }



    @RequestMapping(path = "/test", method = RequestMethod.POST)
    @ResponseBody
    public  String testAjax(String name,int age){
        System.out.println(name);
        System.out.println(age);

        return  CommunityUtil.getJSonString(0,"操作成功");
    }


}
