package com.kgc.service;

import com.kgc.entity.Address;
import com.kgc.entity.Message;

import java.util.Map;

public interface AddressService {
    /**
     * 查询当前用户下的所有地址
     * @param map
     * @return
     */
    Message getAddressListByUserId(Map<String,Object> map);
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
    /**
     * 删除地址
     * @param id
     * @return
     */
    Message deleteAddressById(Integer id);
    /**
     * 修改地址
     * @param address
     * @return
     */
    Message modifyAddressById(Address address);
    /**
     * 获取该用户地址总条数
     * @param userId 登录的用户
     * @return
     */
    Message getAddressCountByUserId(Integer userId);
    /**
     * 当用户选择该地址为默认地址时把其他的地址先改为不默认
     * @param userId
     * @return
     */
    Message modifyNoDefault(Integer userId);
}
