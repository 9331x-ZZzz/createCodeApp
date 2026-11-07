package com.lw.creatcodeapp.service;

import com.lw.creatcodeapp.model.dto.UserQueryRequest;
import com.lw.creatcodeapp.model.vo.LoginUserVO;
import com.lw.creatcodeapp.model.vo.UserVO;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.lw.creatcodeapp.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;
import java.util.List;

/**
 * 用户 服务层。
 *
 * @author <a href="https://github.com/9331x-ZZzz">9331x</a>
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * 根据用户信息获取登录用户视图对象
     * @param  user 用户实体对象，包含用户的基本信息
     * @return LoginUserVO 登录用户视图对象，用于前端展示的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 根据用户信息获取登录用户视图对象
     * @param  userAccount 用户实体对象，包含用户的基本信息
     * @param  userPassword 用户实体对象，包含用户的基本信息
     * @param  request 请求对象，用于获取当前请求的上下文信息
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据用户信息获取登录用户视图对象
     * @param  request 请求对象，用于获取当前请求的上下文信息
     * @return 脱敏后的用户信息
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 获取脱敏后的用户信息
     * @param  user 用户信息
     * @return 脱敏后的用户信息
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏后的用户信息列表
     * @param  userList 用户信息列表
     * @return 脱敏后的用户信息列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 根据查询条件构造查询参数
     * @param  userQueryRequest 用户查询请求对象
     * @return QueryWrapper 查询参数
     */
    QueryWrapper getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 退出登录
     * @param  request 请求对象
     * @return  退出登录是否成功
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 加密用户密码的方法
     * @param userPassword 用户原始密码，长整型格式
     * @return 返回加密后的密码字符串
     */
    String getEncryptPassword(String userPassword);
}
