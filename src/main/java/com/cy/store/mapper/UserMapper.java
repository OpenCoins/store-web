package com.cy.store.mapper;


import com.cy.store.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

//用户模块的持久层接口
@Mapper
public interface UserMapper{
    /**
     * 插入用户的数据
     * @param user 用户的数据
     * @return  受影响的行数（增删改查，都受影响的行数作为返回值，可以根据返回值来判断是否执行成功）
     */
    Integer insert(User user);

    /**
     * 根据用户名来查询用户的数据
     * @param username 用户名
     * @return  如果找到对应的用户则返回这个用户的数据，如果没有找到则返回null值
     */
    User findByUsername(String username);

    /**
     * 根据用户的id来修改密码
     * @param uid 用户的id
     * @return 返回值为受影响的行数
     * @param password 用户输入的新密码
     * @param modifiedUser 表示修改的执行者
     * @param modifiedTime 表示修改数据的时间
     * @return
     */
    Integer updatePassword(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id来查询用户的数据
     * @param uid 用户的id
     * @return 如果找到则返回对象，反之返回null
     */
    User findByUid(Integer uid);

    /**
     * 更新用户的数据信息
     * @param user 用户数据
     * @return 返回值为受影响的行数
     */
    Integer updateByUid(User user);

    /**
     * 根据用户的uid来修改用户的头像
     * @param uid uid
     * @param avatar 头像
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return
     */
    Integer updateAvatarByUid(Integer uid,String avatar,String modifiedUser, Date modifiedTime);
}
