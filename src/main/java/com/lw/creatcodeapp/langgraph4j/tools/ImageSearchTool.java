package com.lw.creatcodeapp.langgraph4j.tools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.lw.creatcodeapp.langgraph4j.model.enums.ImageCategoryEnum;
import com.lw.creatcodeapp.langgraph4j.model.ImageResource;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ImageSearchTool {

    private static final String PEXAFY_API_URL = "https://api.pexafy.com/api/v1/search/photos";

    @Value("${pexafy.api-key}")
    private String pexafyApiKey;

    @Tool("搜索内容相关的图片，用于网站内容展示")
    public List<ImageResource> searchContentImages(@P("搜索关键词") String query) {
        List<ImageResource> imageList = new ArrayList<>();
        int searchCount = 12;
        // 调用 API，注意释放资源
        try (HttpResponse response = HttpRequest.get(PEXAFY_API_URL)
                .header("x-api-key", pexafyApiKey)
                .form("q", query)
                .form("per_page", searchCount)
                .form("page", 1)
                .execute()) {
            if (response.isOk()) {
                JSONObject result = JSONUtil.parseObj(response.body());
                JSONArray data = result.getJSONArray("data");
                for (int i = 0; i < data.size(); i++) {
                    JSONObject photo = data.getJSONObject(i);
                    JSONObject urls = photo.getJSONObject("urls");
                    imageList.add(ImageResource.builder()
                            .category(ImageCategoryEnum.CONTENT)
                            .description(StrUtil.blankToDefault(
                                    photo.getStr("alt_description"), query))
                            .url(urls.getStr("regular"))
                            .build());
                }
            } else {
                log.error("Pexafy API 返回异常状态码: {}, body: {}",
                        response.getStatus(), response.body());
            }
        } catch (Exception e) {
            log.error("Pexafy API 调用失败: {}", e.getMessage(), e);
        }
        return imageList;
    }
}
