package com.example.community.controller;

import com.example.community.entity.User;
import com.example.community.service.UserService;
import com.google.code.kaptcha.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

/**
 * @Description:
 * @Author: lvzil
 * @Date: 2020/2/18 14:43
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);


    @Autowired
    UserService userService;
    @Autowired
    Producer Kaptchaproducer;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "/site/login";
    }

    @RequestMapping(path = "/kaptcha",method = RequestMethod.GET)
    public  void getKaptcha(HttpServletResponse response, HttpSession session){
        //生成验证码
      String text=  Kaptchaproducer.createText();
        //得到一个四位字符串
        BufferedImage image=Kaptchaproducer.createImage(text);

        //存入session
        session.setAttribute("kaptcha",text);
        //将图片输出给浏览器
        response.setContentType("image/png");
        try  {
            OutputStream os = response.getOutputStream();
            ImageIO.write(image,"png",os);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @RequestMapping(path = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return "/site/register";
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String Register(Model model , User user) throws IllegalAccessException {

        Map<String,Object> map=userService.register(user);


        return "/site/register";
    }
}
