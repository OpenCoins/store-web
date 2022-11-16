package com.cy.store.service;


import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.ex.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Test
    public void reg(){
        try {
            User user = new User();
            user.setUsername("小宇是傻逼123");
            user.setPassword("1234");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类的对象和名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }
}
