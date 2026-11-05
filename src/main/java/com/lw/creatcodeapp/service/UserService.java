package com.lw.creatcodeapp.service;

import com.mybatisflex.core.service.IService;
import com.lw.creatcodeapp.model.entity.User;

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
 * 加密用户密码的方法
 * @param userPassword 用户原始密码，长整型格式
 * @return 返回加密后的密码字符串
 */
    String getEncryptPassword(String userPassword);
}
