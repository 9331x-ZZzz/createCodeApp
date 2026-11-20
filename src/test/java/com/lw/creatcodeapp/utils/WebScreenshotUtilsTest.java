package com.lw.creatcodeapp.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WebScreenshotUtilsTest {

    @Test
    void saveWebPageScreenshot() {
        String testurl = "https://www.baidu.com";
        String webPageScreenshot = WebScreenshotUtils.saveWebPageScreenshot(testurl);
        Assertions.assertNotNull(webPageScreenshot);
    }
}