package com.cy.store.service;


import com.cy.store.entity.User;
import org.springframework.stereotype.Service;

//用户模块的业务层接口
public interface UserService {
    /**
     * 用户注册方法
     * @param user 用户的数据对象
     */
    void reg(User user);

}
