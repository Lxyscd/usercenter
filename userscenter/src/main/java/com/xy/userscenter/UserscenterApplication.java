package com.xy.userscenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xy.userscenter.mapper")
public class UserscenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserscenterApplication.class, args);
    }

}
