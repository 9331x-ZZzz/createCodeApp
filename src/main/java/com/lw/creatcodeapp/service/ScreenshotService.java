package com.lw.creatcodeapp.service;

/**
 * 截图服务接口
 * 该接口定义了生成并上传截图的方法
 */
public interface ScreenshotService {
    /**
     * 生成并上传指定网页的截图
     *
     * @param webUrl 需要截图的网页URL地址
     * @return 返回上传结果或截图标识信息
     */
    String generateAndUploadScreenshot(String webUrl);

}
