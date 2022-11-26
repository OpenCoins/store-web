package com.cy.store.entity;

import lombok.Data;

//省市区的数据实体类
@Data
public class District extends BaseEntity{

    private Integer id;
    private String parent;
    private String code;
    private String name;
}
