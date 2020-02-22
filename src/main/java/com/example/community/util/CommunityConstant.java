package com.example.community.util;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/19 17:01
 */
public interface CommunityConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS=0;
    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE=2;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT=1;

    /**
     * 默认状态的登录凭据的超时时间
     */
    int DEFAULT_EXPIRED_SECONDS=3600*12;

    /**
     * 记住状态的登录凭证超时时间
     */
    int REMEMBER_EXPIRED_SECONDS=3600*24*100;

    /**
     * 帖子实体
     */
    int ENTITY_POST=1;
    /**
     * 评论实体
     */
    int ENTITY_COMMENT=2;
}
