package com.cy.store.mapper;


import com.cy.store.entity.Address;
import com.cy.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(22);
        address.setPhone("13956134134");
        address.setName("狗哥");
        addressMapper.insert(address);

    }

    @Test
    public void countByUid(){
        Integer integer = addressMapper.countByUid(22);
        System.out.println(integer);
    }
}
