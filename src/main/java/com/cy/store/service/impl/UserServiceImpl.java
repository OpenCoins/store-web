package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.UserService;
import com.cy.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;


//用户模块的业务层实现类
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //用户注册，将密码用MD5加密处理
    @Override
    public void reg(User user) {
        //通过user参数来获取传递过来的username
        String username = user.getUsername();
        //调用findByUsername(username)判断用户是否被注册过
        User result = userMapper.findByUsername(username);
        //判断结果集是否不为null则抛出用户名被占用的异常
        if (result != null){
            throw new UsernameDuplicatedException("用户被占用");
        }
        //MD5加密 （盐值 + password + 盐值） ---盐值就是一个随机的字符串
        //先获取密码
        String oldPassword = user.getPassword();
        //获取盐值:随机生成一个盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        //补全数据：盐值的记录
        user.setSalt(salt);
        //将密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        //将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);
        //补全数据 0-未删除，1-已删除',
        user.setIsDelete(0);
        //补全数据：4个日志字段信息
        user.setModifiedUser(user.getUsername());
        user.setCreatedUser(user.getUsername());
        Date date = new Date();
        user.setModifiedTime(date);
        user.setCreatedTime(date);
        //执行注册业务功能的实现（rows=1）
        Integer rows = userMapper.insert(user);
        if (rows != 1){
            throw new InsertException("在注册用户过程中产生了未知的异常");
        }
    }

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    @Override
    public User login(String username, String password) {
        //调用mapper层的findByUsername来查询用户的数据
        User result = userMapper.findByUsername(username);
        //根据用户名来查询用户的数据是否存在，如果不在则抛出异常
        if (result == null) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //检测用户的密码是否匹配

        //1.先获取到数据库中加密之后的密码
        String oldPassword = result.getPassword();
        //先获取盐值：上一次注册时所自动生成的盐值
        String salt = result.getSalt();
        //2.1 将用户的密码按照相同的MD5算法的规则进行加密
        String newMd5Password = getMD5Password(password, salt);
        //3.将密码进行比较
        if (!newMd5Password.equals(oldPassword)) {
            throw new PasswordNotMatchException("用户密码错误");
        }

        //判断is_delete字段的值是否为1，表示被标记为删除
        if (result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }

        //将当前的用户数据返回，返回的数据是为了辅助其他页面做数据展示使用的
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        user.setSalt(result.getSalt());

        return user;
    }

    /**
     * 根据用户id来修改密码
     * @param uid id
     * @param username 用户名称
     * @param oldPassword 老密码
     * @param newPassword 新密码
     */
    @Override
    public void update(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        //原始密码和数据库中的密码进行比较
        String oldMd5Password = getMD5Password(oldPassword, result.getSalt());
        if (!result.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, result.getSalt());
        Integer rows = userMapper.updatePassword(uid, newMd5Password, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新数据产生未知的异常");
        }
    }

    //根据用户的id查询用户的数据
    @Override
    public User getByUid(Integer uid) {
        //查询用户的id
        User result = userMapper.findByUid(uid);
        //如果id为空，或用户被删除时，则用户不存在
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    /**
     * user对象中的数据phone\Email\gender，手动将uid/username封装
     * @param user 用户对象的数据user，用户的uid，用户的名称username
     */
    @Override
    public void changeInfo(Integer uid,String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1){
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer updateByUid = userMapper.updateByUid(user);
        if (updateByUid!=1){
            throw new UpdateException("更新数据时产生未知的异常");
        }
    }

    /**
     * 更新用户头像
     * @param uid
     * @param avatar
     * @param username
     */
    @Override
    public void changAvatarByUid(Integer uid, String avatar, String username) {
        //查询当前用户的数据是否存在
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() != 1) {
            throw new UserNotFoundException("该用户不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new UpdateException("更新时产生未知的异常");
        }
    }

    //定义一个MD5加密算法的处理
    private String getMD5Password(String password,String salt){
        for (int i = 0; i < 3; i++) {
            //MD5加密算法的调用（三次加密）
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        //返回加密之后的密码
        return password;
    }
}
