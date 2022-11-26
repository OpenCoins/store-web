package com.cy.store.service;

import com.cy.store.entity.Address;

//收获地址业务层接口
public interface AddressService {

    /**
     *
     * @param uid
     * @param username
     * @param address
     */
    void addNewAddress(Integer uid, String username, Address address);
}
