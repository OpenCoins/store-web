package com.cy.store.controller;

import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username, password);
        //向session对象中完成数据的绑定(session全局)
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());
        return new JsonResult<User>(OK,data);
    }

    //修改密码
    @RequestMapping("/change_password")
    public JsonResult<Void> updatePassword(String oldPassword,String newPassword,HttpSession session){
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.update(uid,username,oldPassword,newPassword);
        return new JsonResult<>(OK);
    }

    //根据用户id查询数据
    @RequestMapping("/get_by_uid")
    public JsonResult<User> getById(HttpSession session){

        User data = userService.getByUid(getuidFromSession(session));
        return new JsonResult<User>(OK,data);
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user,HttpSession session){
        //user对象中有是部分数据 username，phone，Email，gender
        //将uid数据再次封装到user中
        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid,username,user);
        return new JsonResult<>(OK);
    }

    //设置上传文件的最大值
    public static final int AVATAR_MAX_SIZE = 10*1024*1024;
    //限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * 上传头像
     * @param session
     * @param file  接口
     * @return
     */
    @RequestMapping("/chang_avatar")
    public JsonResult<String> changAvatar(HttpSession session,@RequestParam("file") MultipartFile file){

    }
}
