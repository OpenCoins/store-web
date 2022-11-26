package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.service.AddressService;
import com.cy.store.service.DistrictService;
import com.cy.store.service.ex.AddressCountLimitException;
import com.cy.store.service.ex.InsertException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

//新增收获地址的实现类
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    //添加用户收获地址业务层
    @Autowired
    private DistrictService districtService;

//    @Value("${user.address.max-count}")
//    private Integer maxCount;

    /**
     *
     * @param uid
     * @param username
     * @param address
     */
    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        //调用收获地址统计的方法
        Integer count = addressMapper.countByUid(uid);
        if (count >= 20){
            throw new AddressCountLimitException("用户收获地址超出上线");
        }

        //对address中的数据进行补全，省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);


        //uid,isDefault
        address.setUid(uid);
        Integer isDefault = count == 0 ? 1 : 0;  // 1 表示默认，0 表示不默认
        address.setIsDefault(isDefault);
        //补全四项日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        //插入收获地址的方法
        Integer row = addressMapper.insert(address);
        if (row!=1){
            throw new InsertException("插入收货地址时产生未知的异常");
        }
    }
}
