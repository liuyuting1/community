package com.example.community.service;

import com.example.community.dao.MessageMapper;
import com.example.community.entity.Message;
import com.example.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/3/4 15:07
 */
@Service
public class MessageService {

    @Autowired
    MessageMapper messageMapper;
    @Autowired
    SensitiveFilter sensitiveFilter;

    public List<Message> findConversations(int userId,int offset,int limit){
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId){
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId,int offset,int limit){
        return  messageMapper.selectLetters(conversationId, offset, limit);
    }
    public  int findLetterCount(String conversationId){
        return messageMapper.selectLetterCount(conversationId);
    }
    public  int findLetterUnreadCount(int userId,String conversationID){
        return messageMapper.selectLetterUnreadCount(userId, conversationID);
    }
    public  int addMessage(Message message){
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    public Message findLatestNotice(int userId,String topic){
        return messageMapper.selectLatestNotice(userId, topic);
    }

    public  int findNoticeCount(int userId,String topic){
        return  messageMapper.selectNoticeCount(userId, topic);
    }

    public  int findNoticeUnreadCount(int userID,String topic){
        return  messageMapper.selectNoticeUnreadCount(userID, topic);
    }

    public List<Message> findNotices(int userID,String topic,int offset,int limit){
        return messageMapper.selectNotices(userID, topic, offset, limit);
    }



}
