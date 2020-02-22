package com.example.community.service;

import com.example.community.dao.DiscussPostMapper;
import com.example.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/17 14:26
 */
@Service
public class DiscusspostService {

    @Autowired
    DiscussPostMapper discussPostMapper;

    public List<DiscussPost> findDiscussPost(int userid,int offset,int limit){
        return discussPostMapper.selectDiscussPosts(userid,offset,limit);
    }

    public  int findDiscussrows(int id){
        return discussPostMapper.selectDiscussPostRows(id);
    }

    public  int addDiscussPost(DiscussPost post){
        if(post==null)
            throw  new IllegalArgumentException("参数不能为空");
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));

        return  discussPostMapper.insertDiscussPost(post);
    }

    public  DiscussPost findDiscussPostById(int id){
        return  discussPostMapper.selectDiscussPostById(id);
    }
//    public  int updateCommentCount(int id,int commentCount){
//        return  discussPostMapper.updateCommentCount(id,commentCount);
//    }
}
