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

    /**
     * 查询地址详情
     * @param id 地址id
     * @return
     */
    Address getAddressById(Integer id);

    /**
     * 添加地址
     * @param address 需要添加的地址信息
     * @return
     */
    Integer addAddress(Address address);
}
