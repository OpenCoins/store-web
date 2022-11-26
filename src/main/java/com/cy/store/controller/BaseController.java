package com.cy.store.controller;

import com.cy.store.controller.ex.FileEmptyException;
import com.cy.store.controller.ex.FileSizeException;
import com.cy.store.controller.ex.FileStateException;
import com.cy.store.controller.ex.FileUploadException;
import com.cy.store.service.ex.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

//控制层类的基类
public class BaseController {
    /**操作成功的状态码**/
    public static final int OK = 200;

    //请求处理方法，这个方法的返回值就是需要传递给前端的数据
    //自动将异常对象传递给此方法的参数列表上
    @ExceptionHandler(ServiceException.class) //用于统一处理抛出的异常
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicatedException) {
            result.setState(4000);
            result.setMassage("用户名被占用的异常");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMassage("用户数据不存在的异常");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMassage("用户名的密码错误的异常");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMassage("用户的地址超出上限的异常");
        } else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMassage("插入数据时产生未知异常");
        }else if (e instanceof UpdateException) {
            result.setState(5003);
            result.setMassage("更新数据时产生未知异常");
        }else if (e instanceof FileEmptyException) {
            result.setState(5004);

        }else if (e instanceof FileSizeException) {
            result.setState(5005);

        }else if (e instanceof FileUploadException) {
            result.setState(5006);

        }else if (e instanceof FileStateException) {
            result.setState(5007);

        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session
     * @return 当前登录用户uid的值
     */
    protected final Integer getuidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     *获取当前登录用户的username
     * @param session
     * @return 当前登录用户的用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
