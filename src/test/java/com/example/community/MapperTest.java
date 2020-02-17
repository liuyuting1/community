package com.example.community;

import com.example.community.dao.DiscussPostMapper;
import com.example.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/17 14:12
 */
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {

    @Autowired
    DiscussPostMapper discussPostMapper;

    @Test
    public  void testSelectPosts(){
        List<DiscussPost> list=discussPostMapper.selectDiscussPosts(111,1,10);
        for(DiscussPost post:list){
            System.out.println(post);
        }
       int k= discussPostMapper.selectDiscussPostRows(0);
        System.out.println(k);
    }

}
