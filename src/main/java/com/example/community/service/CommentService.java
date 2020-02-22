package com.example.community.service;

import com.example.community.dao.CommentMapper;
import com.example.community.entity.Comment;
import com.example.community.util.CommunityConstant;
import com.example.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/21 13:48
 */
@Service
public class CommentService implements CommunityConstant {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    private  DiscusspostService discusspostService;

    @Autowired
    private SensitiveFilter sensitiveFilter;
    /**
     * findCommentsByEntity
     * findCommentCount
     */
    public List<Comment> findCommentsByEntity(int entityTYpe,int entityId,int offset,int limit){
        return commentMapper.selectCommentsByEntity(entityTYpe, entityId, offset, limit);
    }

    public  int findCommentCount(int entityType,int entityId){
        return commentMapper.selectCountByEntity(entityType, entityId);
    }

    /**
     * 由spring来负责数据库的打开，提交，回滚
     * 读取已提交的数据。如果有事务就加入事务，没有就创建一个
     * @param comment
     * @return
     */
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public  int addComment(Comment comment){
       if(comment==null)
           throw new IllegalArgumentException("参数不能为空！");
       comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
       comment.setContent(sensitiveFilter.filter(comment.getContent()));
       int rows=commentMapper.insertComment(comment);

       //更新帖子评论数量
        if(comment.getEntity_type()==ENTITY_POST){
            int count=commentMapper.selectCountByEntity(comment.getEntity_type(),comment.getEntity_id());
          //  discusspostService.updateCommentCount(comment.getEntity_id(),count);
        }

        return  rows;
    }

}
