package com.example.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}
/**
 * 启动了tomcat，自动创建Spring容器。自动扫描某些包下的某些bean。
 * 四个注解由commonent实现，处理请求：controller。bean 用component。访问数据库用repository.
 * @scope("prototype") 每次访问bean都会创建一个实例。
 * 希望在容器中装配一个第三方的jar包，需要自己写一个配置类 @configuration 方法前注解 @Bean
 *spring依赖注入 免去实例化
 *
 */
}
