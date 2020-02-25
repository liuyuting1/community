package com.example.community.dao;

import com.example.community.entity.Comment;
import com.example.community.util.CommunityUtil;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/21 13:24
 */
@Mapper
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

    int selectUserIDbyId(int id);


}
