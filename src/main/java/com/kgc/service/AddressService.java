package com.kgc.service;

import com.kgc.entity.Address;
import com.kgc.entity.Message;

public interface AddressService {
    /**
     * 查询当前用户下的所有地址
     * @param userId
     * @return
     */
    Message getAddressListByUserId(Integer userId);
    /**
     * 查询地址详情
     * @param id 地址id
     * @return
     */
    Message getAddressById(Integer id);

    /**
     * 添加地址
     * @param address 需要添加的地址信息
     * @return
     */
    Message addAddress(Address address);
}
