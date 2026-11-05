package com.lw.creatcodeapp.service;

import com.lw.creatcodeapp.model.vo.LoginUserVO;
import com.mybatisflex.core.service.IService;
import com.lw.creatcodeapp.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.net.http.HttpRequest;

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


    User getLoginUser(HttpServletRequest request);

    /**
     * 加密用户密码的方法
     * @param userPassword 用户原始密码，长整型格式
     * @return 返回加密后的密码字符串
     */
    String getEncryptPassword(String userPassword);
}
