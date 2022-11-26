package com.cy.store.mapper;

import com.cy.store.entity.Address;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {

    /**
     * 插入用户收入地址的数据
     * @param address 收货地址数据
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     * 根据用户的id统计收货地址的数量
     * @param uid 用户id
     * @return 当前用户的收获地址总数
     */
    Integer countByUid(Integer uid);
}
