package com.lw.creatcodeapp.service;

import com.lw.creatcodeapp.model.dto.chathistory.ChatHistoryQueryRequest;
import com.lw.creatcodeapp.model.entity.ChatHistory;
import com.lw.creatcodeapp.model.entity.User;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import java.time.LocalDateTime;


    /**
     * 对话历史 服务层。
     *
     * @author <a href="https://github.com/9331x-ZZzz">9331x</a>
     */
    public interface ChatHistoryService extends IService<ChatHistory> {

    /**
     * 添加聊天消息的方法
     * @param appId 应用ID，用于标识不同的应用
     * @param message 聊天消息内容
     * @param messageType 消息类型，用于区分不同种类的消息
     * @param userId 用户ID，标识发送消息的用户
     * @return 返回一个布尔值，表示消息添加是否成功
     */
    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    /**
     * 删除聊天消息的方法
     *
     * @param appId 应用程序ID，用于标识特定的应用程序
     * @return 返回一个布尔值，表示删除操作是否成功执行
     *         true表示删除成功
     *         false表示删除失败
     */
    boolean deleteByAppId(Long appId);

    /**
     * 根据聊天历史查询请求参数获取查询构造器
     * @param chatHistoryQueryRequest 聊天历史查询请求参数对象，包含查询条件
     * @return 返回一个QueryWrapper对象，用于构建数据库查询条件
     */
    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);


    /**
     * 分页查询应用聊天历史记录
     *
     * @param appId 应用ID，用于指定要查询哪个应用的聊天记录
     * @param pageSize 每页记录数，用于控制分页大小
     * @param lastCreateTime 上一次查询的最后一条记录的创建时间，用于分页查询
     * @param loginUser 登录用户信息，用于权限验证
     * @return 返回一个包含聊天历史记录的分页对象
     */
    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);
}
