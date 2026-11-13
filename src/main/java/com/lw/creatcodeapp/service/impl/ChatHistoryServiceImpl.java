package com.lw.creatcodeapp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lw.creatcodeapp.constant.UserConstant;
import com.lw.creatcodeapp.exception.ErrorCode;
import com.lw.creatcodeapp.exception.ThrowUtils;
import com.lw.creatcodeapp.mapper.ChatHistoryMapper;
import com.lw.creatcodeapp.model.dto.chathistory.ChatHistoryQueryRequest;
import com.lw.creatcodeapp.model.entity.App;
import com.lw.creatcodeapp.model.entity.ChatHistory;
import com.lw.creatcodeapp.model.entity.User;
import com.lw.creatcodeapp.model.enums.ChatHistoryMessageTypeEnum;
import com.lw.creatcodeapp.service.AppService;
import com.lw.creatcodeapp.service.ChatHistoryService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层实现。
 *
 * @author <a href="https://github.com/9331x-ZZzz">9331x</a>
 */
@Service
public class ChatHistoryServiceImpl extends ServiceImpl<ChatHistoryMapper, ChatHistory>  implements ChatHistoryService{

    @Resource
    @Lazy
    private AppService appService;
/**
 * 添加聊天消息
 * @param appId 应用ID
 * @param message 消息内容
 * @param messageType 消息类型
 * @param userId 用户ID
 * @return 是否添加成功
 */
    @Override
    public boolean addChatMessage(Long appId, String message, String messageType, Long userId) {
        // 验证应用ID是否有效
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        // 验证消息内容是否为空
        ThrowUtils.throwIf(StrUtil.isBlank(message), ErrorCode.PARAMS_ERROR, "消息内容不能为空");
        // 验证消息类型是否为空
        ThrowUtils.throwIf(StrUtil.isBlank(messageType), ErrorCode.PARAMS_ERROR, "消息类型不能为空");
        // 验证用户ID是否有效
        ThrowUtils.throwIf(userId == null || userId <= 0, ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        // 验证消息类型是否有效
        ChatHistoryMessageTypeEnum messageTypeEnum = ChatHistoryMessageTypeEnum.getEnumByValue(messageType);
        ThrowUtils.throwIf(messageTypeEnum == null, ErrorCode.PARAMS_ERROR, "不支持的消息类型: " + messageType);
        // 构建聊天历史记录对象
        ChatHistory chatHistory = ChatHistory.builder()
                .appId(appId)
                .message(message)
                .messageType(messageType)
                .userId(userId)
                .build();
        // 保存聊天记录并返回保存结果
        return this.save(chatHistory);
    }

    @Override
    public boolean deleteByAppId(Long appId) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        QueryWrapper queryWrapper = QueryWrapper.create()
                .eq("appId", appId);
        return this.remove(queryWrapper);
    }

    /**
     * 获取查询包装类
     * 用于根据聊天历史查询请求构建查询条件，支持多种查询条件和排序方式
     *
     * @return QueryWrapper 构建好的查询条件包装器
     */
    @Override
    public QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest) {
        // 创建查询包装器实例
        QueryWrapper queryWrapper = QueryWrapper.create();
        // 如果查询请求为空，直接返回空的查询包装器
        if (chatHistoryQueryRequest == null) {
            return queryWrapper;
        }
        // 从查询请求中获取各个查询条件的值
        Long id = chatHistoryQueryRequest.getId();
        String message = chatHistoryQueryRequest.getMessage();
        String messageType = chatHistoryQueryRequest.getMessageType();
        Long appId = chatHistoryQueryRequest.getAppId();
        Long userId = chatHistoryQueryRequest.getUserId();
        LocalDateTime lastCreateTime = chatHistoryQueryRequest.getLastCreateTime();
        String sortField = chatHistoryQueryRequest.getSortField();
        String sortOrder = chatHistoryQueryRequest.getSortOrder();
        // 拼接查询条件 - 使用等值查询和模糊查询
        queryWrapper.eq("id", id)          // ID等值查询
                .like("message", message)  // 消息内容模糊查询
                .eq("messageType", messageType)  // 消息类型等值查询
                .eq("appId", appId)        // 应用ID等值查询
                .eq("userId", userId);     // 用户ID等值查询
        // 游标查询逻辑 - 只使用 createTime 作为游标
        // 如果设置了最后创建时间，则查询创建时间小于该时间的数据
        if (lastCreateTime != null) {
            queryWrapper.lt("createTime", lastCreateTime);
        }
        // 排序逻辑处理
        // 如果指定了排序字段，则按指定字段和排序方式排序
        if (StrUtil.isNotBlank(sortField)) {
            queryWrapper.orderBy(sortField, "ascend".equals(sortOrder));
        } else {
            // 默认按创建时间降序排列
            queryWrapper.orderBy("createTime", false);
        }
        return queryWrapper;
    }

    @Override
    public Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                                      LocalDateTime lastCreateTime,
                                                      User loginUser) {
        ThrowUtils.throwIf(appId == null || appId <= 0, ErrorCode.PARAMS_ERROR, "应用ID不能为空");
        ThrowUtils.throwIf(pageSize <= 0 || pageSize > 50, ErrorCode.PARAMS_ERROR, "页面大小必须在1-50之间");
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NOT_LOGIN_ERROR);
        // 验证权限：只有应用创建者和管理员可以查看
        App app = appService.getById(appId);
        ThrowUtils.throwIf(app == null, ErrorCode.NOT_FOUND_ERROR, "应用不存在");
        boolean isAdmin = UserConstant.ADMIN_ROLE.equals(loginUser.getUserRole());
        boolean isCreator = app.getUserId().equals(loginUser.getId());
        ThrowUtils.throwIf(!isAdmin && !isCreator, ErrorCode.NO_AUTH_ERROR, "无权查看该应用的对话历史");
        // 构建查询条件
        ChatHistoryQueryRequest queryRequest = new ChatHistoryQueryRequest();
        queryRequest.setAppId(appId);
        queryRequest.setLastCreateTime(lastCreateTime);
        QueryWrapper queryWrapper = this.getQueryWrapper(queryRequest);
        // 查询数据
        return this.page(Page.of(1, pageSize), queryWrapper);
    }

}
