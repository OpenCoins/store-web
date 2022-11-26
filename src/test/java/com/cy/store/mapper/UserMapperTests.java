package com.cy.store.mapper;


import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert(){
        User user = new User();
        user.setUsername("test100");
        user.setPassword("288100Xmazn");
        Integer insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    public void findByUsername(){
        User user = userMapper.findByUsername("阿祖");
        System.out.println(user);
    }

    @Test
    public void updatePassword(){
        userMapper.updatePassword(17,"288","管理员",new Date());
    }

    @Test
    public void findByUid(){
        System.out.println(userMapper.findByUid(17));
    }

    @Test
    public void updateInfoByUid(){
        User user = new User();
        user.setUid(22);
        user.setPhone("13956134134");
        user.setEmail("288100@163.com");
        user.setGender(1);
        userMapper.updateByUid(user);
    }

    @Test
    public void updateAvatarByUid(){
        userMapper.updateAvatarByUid(22,"/upload/avatar.png","管理员",new Date());
    }
}
