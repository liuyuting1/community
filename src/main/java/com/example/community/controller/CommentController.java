package com.example.community.controller;

import com.example.community.entity.Comment;
import com.example.community.service.CommentService;
import com.example.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/25 08:22
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    HostHolder hostHolder;


    @RequestMapping(path = "/add/{discusspostId}",method = RequestMethod.POST)
    public  String addComment(@PathVariable("discusspostId") int discussId,  Comment comment,
                              @RequestParam("entityType") int entityType,
                              @RequestParam("entityId") int entityId
                              ){

        System.out.println(entityId);
        System.out.println(entityType);
        comment.setEntity_id(entityId);
        comment.setEntity_type(entityType);
        comment.setUser_id(hostHolder.getUser().getId());
        comment.setCreate_time(new Date());
        comment.setStatus(0);
        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussId;
    }

}
