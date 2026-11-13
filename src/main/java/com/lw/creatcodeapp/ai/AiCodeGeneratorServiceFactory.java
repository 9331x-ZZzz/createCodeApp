package com.lw.creatcodeapp.ai;

import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * AI 服务创建工厂
 */
@Configuration
@Slf4j
public class AiCodeGeneratorServiceFactory {

        @Resource
        private ChatModel chatModel;

        @Resource
        private StreamingChatModel streamingChatModel;

        @Resource
        private RedisChatMemoryStore redisChatMemoryStore;

/**
 * 根据应用ID获取AI代码生成服务实例
 * 该方法会为每个appId创建独立的对话记忆，确保不同应用之间的对话上下文相互隔离
 *
 * @param appId 应用ID，用于构建独立的对话记忆
 * @return AiCodeGeneratorService 配置了独立对话记忆的AI代码生成服务实例
 */
        public AiCodeGeneratorService getAiCodeGeneratorService(long appId) {
                //根据 appId 构建独立的对话记忆，使用Redis作为持久化存储
                MessageWindowChatMemory chatMemory = MessageWindowChatMemory
                        .builder()
                        .id(appId)                    // 设置记忆ID，使用appId作为唯一标识
                        .chatMemoryStore(redisChatMemoryStore)  // 使用Redis作为聊天记忆存储
                        .maxMessages(20)             // 设置最大记忆消息数量为20条
                        .build();
    // 构建并返回AI代码生成服务实例，配置了聊天模型、流式聊天模型和对话记忆
                return AiServices.builder(AiCodeGeneratorService.class)
                        .chatModel(chatModel)           // 配置聊天模型
                        .streamingChatModel(streamingChatModel)  // 配置流式聊天模型
                        .chatMemory(chatMemory)         // 配置对话记忆
                        .build();
        }


        @Bean
        public AiCodeGeneratorService aiCodeGeneratorService() {
                return getAiCodeGeneratorService(0);
        }


}

