package com.example.community.dao;

import com.example.community.entity.Message;
import com.sun.mail.imap.protocol.ListInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/27 11:47
 */
@Mapper
public interface MessageMapper {

    List<Message> selectConversations(int userId, int pffset, int limit);

    //查询当前用户的会话数量
    int selectConversationCount(int userId);

//    查询某个会话所包含的私信列表

    List<Message> selectLetters(String conversationId, int offset, int limit);

    //查询某个会话所包含的私信数量
    int selectLetterUnreadCount(String ConversationId);

    //查询未读私信的数量
    int selectLetterUnreadCount(int userId, String conversationId);

    //新增消息
    int insertMessage(Message message);

    //修改消息的状态
    int updateStatus(List<Integer> ids, int status);

    //查询某个主题下最新的通知
    Message selectLatestNotice(int userId, String topic);

    //查询某个主题下所包含的通知数量
    int selectNoticeCount(int userId,String topic);
    //查询未读的通知数量
    int selectNoticeUnreadCount(int userId,String topic);

    //查询某个主题所包含的通知列表
    List<Message> selectNotices(int userId,String topic,int office,int limit);

}













