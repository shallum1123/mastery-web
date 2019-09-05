package com.ytxd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Objects;

@EnableEurekaClient
@MapperScan("com.ytxd.dao")
@SpringBootApplication
public class MasteryWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MasteryWebApplication.class, args);
    }

}
