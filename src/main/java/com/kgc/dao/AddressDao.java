package com.kgc.dao;

import com.kgc.entity.Address;

import java.util.List;

public interface AddressDao {
    /**
     * 查询当前用户下的所有地址
     * @param userId
     * @return
     */
    List<Address> getAddressListByUserId(Integer userId);
}
