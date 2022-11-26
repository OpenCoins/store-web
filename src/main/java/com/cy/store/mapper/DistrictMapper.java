package com.cy.store.mapper;

import com.cy.store.entity.District;

import java.util.List;

public interface DistrictMapper {

    /**
     * 根据父代号查询区域信息
     * @param parent 父代号
     * @return 某个父区域下的所有区域代表
     */
    List<District> findByParent(String parent);

    /**
     *
     * @param code
     * @return
     */
    String getNameByCode(String code);
}
