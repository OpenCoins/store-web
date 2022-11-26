package com.cy.store.service.impl;

import com.cy.store.entity.District;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//业务层
@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> getByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        //在进行网络传输时，为了尽量避免无效数据，可以将无效数据设置为null
        for (District d:list) {
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        String name = districtMapper.getNameByCode(code);
        return name;
    }

}
