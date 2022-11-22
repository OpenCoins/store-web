package com.cy.store.service;


import com.cy.store.entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

//用户模块的业务层接口
public interface UserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username
     * @param password
     * @return 当前匹配的用户数据，如果没有则返回null
     */
    User login(String username,String password);

    /**
     * 修改密码
     * @param uid
     * @param newPassword
     * @param oldPassword
     */
    void update(Integer uid, String username, String oldPassword, String newPassword);

    /**
     * 根据用户的id查询用户的数据
     * @param uid 用户的id
     * @return 返回用户的数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据
     * @param user 用户对象的数据user，用户的uid，用户的名称username
     */
    void changeInfo(Integer uid,String username, User user);

    /**
     * 更新用户的头像
     * @param uid
     * @param avatar
     * @param username
     */
    void changAvatarByUid(Integer uid,String avatar,String username);
}
