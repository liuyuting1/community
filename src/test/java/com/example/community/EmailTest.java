package com.example.community;

import com.example.community.util.MailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/18 15:23
 */

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class EmailTest {

    @Autowired
    MailClient mailClient;
    @Autowired
    private TemplateEngine templateEngine;


    @Test
    public  void  testTextMail(){
        mailClient.sendMail("1757668010@qq.com","test","success");
    }

    @Test
    public void testHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "sunday");

        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);

        mailClient.sendMail("1757668010@qq.com", "HTML", content);
    }

}
