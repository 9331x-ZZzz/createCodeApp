package com.lw.creatcodeapp.ai;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Autowired
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCode() {
        String result = aiCodeGeneratorService.generateHtmlCode("请生成一个9331x的博客，不超过20行");
        Assertions.assertNotNull(result);
    }

    @Test
    void generateMultifileCode() {
        String result = aiCodeGeneratorService.generateMultifileCode("请生成一个9331x的博客留言板，不超过50行");
        Assertions.assertNotNull(result);
    }
}