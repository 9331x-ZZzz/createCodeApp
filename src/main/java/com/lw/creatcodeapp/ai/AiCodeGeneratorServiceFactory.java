package com.lw.creatcodeapp.ai;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.langchain4j.community.store.memory.chat.redis.RedisChatMemoryStore;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;


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
         * 创建新的 AI 服务实例
         *
         *
         * @param appId 应用ID，用于构建独立的对话记忆
         * @return AiCodeGeneratorService 配置了独立对话记忆的AI代码生成服务实例
         */
        public AiCodeGeneratorService createAiCodeGeneratorService(long appId) {
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


        /**
         * AI 服务实例缓存
         * 缓存策略：
         * - 最大缓存 1000 个实例
         * - 写入后 30 分钟过期
         * - 访问后 10 分钟过期
         */
        private final Cache<Long, AiCodeGeneratorService> serviceCache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofMinutes(30))
                .expireAfterAccess(Duration.ofMinutes(10))
                .removalListener((key, value, cause) -> {
                        log.debug("AI 服务实例被移除，appId: {}, 原因: {}", key, cause);
                })
                .build();

        /**
         * 根据 appId 获取服务（带缓存）
         */
        public AiCodeGeneratorService getAiCodeGeneratorService(long appId) {
                return serviceCache.get(appId, this::createAiCodeGeneratorService);
        }

        /**
         * 根据 appId 获取服务（为了兼容老逻辑）
         *
         * @param
         * @return
         */
        @Bean
        public AiCodeGeneratorService aiCodeGeneratorService() {
                return getAiCodeGeneratorService(0);
        }


}

