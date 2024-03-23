package com.kgc.service;

import com.kgc.entity.Message;

public interface AddressService {
    /**
     * 查询当前用户下的所有地址
     * @param userId
     * @return
     */
    Message getAddressListByUserId(Integer userId);
}
