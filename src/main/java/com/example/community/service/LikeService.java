package com.example.community.service;

import com.example.community.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/27 16:17
 */
@Service
public class LikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    public  void like(int userId,int entityType,int entityId,int entityUserId) {

        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {

                String entityLikeKey = RedisUtil.getEntityLikeKey(entityType, entityId);
                String userLikeKey = RedisUtil.getUserLikeKey(entityUserId);
                boolean isMember = redisOperations.opsForSet().isMember(entityLikeKey, userId);//判断用户是否点过赞
                redisOperations.multi();
                if (isMember) {
                    redisOperations.opsForSet().remove(entityLikeKey, userId);//取消点赞
                    redisOperations.opsForValue().decrement(userLikeKey);
                } else {
                    redisOperations.opsForSet().add(entityLikeKey, userId);//点赞
                    redisOperations.opsForValue().increment(userLikeKey);
                }
                return redisOperations.exec();
            }
        });
    }

        // 查询某实体点赞的数量
        public long findEntityLikeCount(int entityType,int entityId){
            String entityLikeKey=RedisUtil.getEntityLikeKey(entityType,entityId);
            return redisTemplate.opsForSet().size(entityLikeKey);
        }

        // 查询某人对某实体的点赞状态
        public int findEntityLikeStatus(int userId, int entityType, int entityId) {
            String entityLikeKey = RedisUtil.getEntityLikeKey(entityType, entityId);
            return redisTemplate.opsForSet().isMember(entityLikeKey, userId) ? 1 : 0;
        }

        // 查询某个用户获得的赞
        public int findUserLikeCount(int userId) {
            String userLikeKey = RedisUtil.getUserLikeKey(userId);
            Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
            return count == null ? 0 : count.intValue();
        }

    }

