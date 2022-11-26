package com.cy.store.controller;

import com.cy.store.controller.ex.FileEmptyException;
import com.cy.store.controller.ex.FileSizeException;
import com.cy.store.controller.ex.FileTypeException;
import com.cy.store.controller.ex.FileUploadException;
import com.cy.store.entity.User;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.InsertException;
import com.cy.store.service.ex.UsernameDuplicatedException;
import com.cy.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        AVATAR_TYPE.add("image/jpg");
    }

    /**
     * 上传头像
     * @param session
     * @param file  接口
     * @return
     */
    @RequestMapping("/chang_avatar")
    public JsonResult<String> changAvatar(HttpSession session,@RequestParam("file") MultipartFile file){
        //判断文件是否为null
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }
        if (file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件超出范围限制");
        }
        //判断文件的类型是否是我们规定的后缀类型
        String contentType = file.getContentType();
        //如果包含集合中的某个元素，返回ture
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不一样");
        }
        //上传的文件.../upload/文件。png
        String realPath = session.getServletContext().getRealPath("/upload");
        //file对象指向这个路径，file是否存在
        File dir = new File(realPath);
        if (!dir.exists()){  //检测目录是否存
            dir.mkdirs();  //如果不存在，创建当前的目录
        }
        //获取到这个文件的名称，用uuid工具来生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        int index = originalFilename.lastIndexOf(".");
        String substring = originalFilename.substring(index);
        String filename = UUID.randomUUID().toString().toUpperCase() + substring;
        //生成一个空文件
        File dest = new File(dir,filename);

        //将参数file中的数据写入到这个空文件中
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new FileUploadException("文件读写异常");
        }

        Integer uid = getuidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" + filename;
        userService.changAvatarByUid(uid,avatar,username);
        //返回用户头像的路径给前端页面，将来给前端做展示使用
        return new JsonResult<>(OK,avatar);
    }
}
