package com.lw.creatcodeapp;

import dev.langchain4j.community.store.embedding.redis.spring.RedisEmbeddingStoreAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {RedisEmbeddingStoreAutoConfiguration.class})
@MapperScan("com.lw.creatcodeapp.mapper")
public class CreatCodeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(CreatCodeAppApplication.class, args);
    }

}
