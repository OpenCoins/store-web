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
            user.setUsername("test009");
            user.setPassword("123456");
            userService.reg(user);
            System.out.println("OK");
        } catch (ServiceException e) {
            //获取类的对象和名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常的具体描述信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login(){
        User user = userService.login("Test001", "1234");
        System.out.println(user);
    }

    @Test
    public void update(){
        userService.update(22,"管理员","288100","345678");
    }


    @Test
    public void getById(){
        System.out.println(userService.getByUid(22));;
    }


    @Test
    public void changeInfo(){
        User user = new User();
        user.setPhone("123456789");
        user.setEmail("288100@qq.com");
        user.setGender(0);
        userService.changeInfo(22,"管理员",user);

    }
}
