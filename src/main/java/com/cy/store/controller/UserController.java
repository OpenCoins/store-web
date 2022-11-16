package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    @RequestMapping("/reg")
    //@PostMapping("/reg")
    public JsonResult<Void> reg(User user) {
        //创建响应结果对象
        userService.reg(user);
        return new JsonResult<>(OK);
    }
}
