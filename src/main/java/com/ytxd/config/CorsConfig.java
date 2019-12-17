package com.ytxd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author Shallum
 * @Date 2019/10/10 11:02
 * @Version 1.0
 * 解决跨域问题
 */
@Configuration
public class CorsConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("origin", "content-type", "accept", "x-requested-with")
                .allowCredentials(false).maxAge(3600);
        super.addCorsMappings(registry);
    }

}
