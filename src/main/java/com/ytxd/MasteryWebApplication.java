package com.ytxd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@EnableEurekaClient
@MapperScan("com.ytxd.dao")
@SpringBootApplication
public class MasteryWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MasteryWebApplication.class, args);
    }

    /**
     * 文件上传临时路径
     * 在Spring Boot下配置location，可以在main()方法所在文件中添加如下代码：
     */
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("data/tmp");
        return factory.createMultipartConfig();
    }

}

