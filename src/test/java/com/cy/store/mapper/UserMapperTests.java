//package com.cy.store.mapper;
//
//
//import com.cy.store.entity.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class UserMapperTests {
//
//    @Autowired
//    private UserMapper userMapper;
//
//    @Test
//    public void insert(){
//        User user = new User();
//        user.setUsername("阿祖");
//        user.setPassword("1234");
//        Integer insert = userMapper.insert(user);
//        System.out.println(insert);
//    }
//
//    @Test
//    public void findByUsername(){
//        User user = userMapper.findByUsername("阿祖");
//        System.out.println(user);
//    }
//}
