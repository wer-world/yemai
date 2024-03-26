package com.kgc.service.impl;

import com.kgc.dao.AddressDao;
import com.kgc.entity.Address;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.AddressService;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;
    @Override
    public Message getAddressListByUserId(Map<String,Object> map) {
        List<Address> addressListByUserId = addressDao.getAddressListByUserId(map);
        return Message.success(addressListByUserId);
    }

    @Override
    public Message getAddressById(Integer id) {
        Address addressById = addressDao.getAddressById(id);
        return Message.success(addressById);
    }

    @Override
    public Message addAddress(Address address) {
        if (address == null || address.getAddress().isEmpty()) {
            return  Message.error();
        }
        if (address.getIsDefault() == 1) {
            addressDao.modifyNoDefault(address.getUserId());
        }
        Integer count = addressDao.addAddress(address);
        if (count != 1) {
            return Message.error();
        }

        return Message.success();
    }

    @Override
    public Message deleteAddressById(Integer id) {
        Integer count = addressDao.deleteAddressById(id);
        if (count != 1) {
            return Message.error();
        }
        return Message.success();
    }

    @Override
    public Message modifyAddressById(Address address) {
        if (address == null || address.getAddress().isEmpty()) {
            return Message.error();
        }
        if (address.getIsDefault() == 1) {
            addressDao.modifyNoDefault(address.getUserId());
        }
        Integer count = addressDao.modifyAddressById(address);
        if (count != 1) {
            return Message.error();
        }
        return Message.success();
    }

    @Override
    public Message getAddressCountByUserId(Integer userId) {
        Integer count = addressDao.getAddressCountByUserId(userId);
        if (count == 0) {
            return Message.error();
        }
        return Message.success(count);
    }

    @Override
    public Message modifyNoDefault(Integer userId) {
        Integer count = addressDao.modifyNoDefault(userId);
        if (count !=1){
            return Message.error();
        }
        return Message.success();
    }
}
