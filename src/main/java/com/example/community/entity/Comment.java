package com.example.community.entity;

import java.util.Date;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/21 13:20
 */
public class Comment {
    public int id;
    public int userId;
    public int entityType;
    public  int entityId;
    public  int targetId;
    public  String content;
    public  int status;
    public Date createTime;

    public int getTarget_id() {
        return targetId;
    }

    public void setTarget_id(int target_id) {
        this.targetId = target_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public int getEntity_type() {
        return entityType;
    }

    public void setEntity_type(int entity_type) {
        this.entityType = entity_type;
    }

    public int getEntity_id() {
        return entityId;
    }

    public void setEntity_id(int entity_id) {
        this.entityId = entity_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return createTime;
    }

    public void setCreate_time(Date create_time) {
        this.createTime = create_time;
    }



}
