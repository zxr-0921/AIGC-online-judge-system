package com.zxr.aiojbakcend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
// todo 如需开启 Redis，须移除 exclude 中的内容
//@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@SpringBootApplication
@MapperScan("com.zxr.aiojbakcend.mapper")
public class AiojBakcendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiojBakcendApplication.class, args);
    }

}
