package com.lw.creatcodeapp.ai;

import com.lw.creatcodeapp.ai.model.HtmlCodeResult;
import com.lw.creatcodeapp.ai.model.MultiFileCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    @Test
    void generateHtmlCode() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("请生成一个9331x的博客，不超过20行");
        Assertions.assertNotNull(result);
    }

    @Test
    void generateMultifileCode() {
        MultiFileCodeResult result = aiCodeGeneratorService.generateMultiFileCode("请生成一个9331x的博客留言板，不超过50行");
        Assertions.assertNotNull(result);
    }
}