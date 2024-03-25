package com.kgc.service.impl;

import com.kgc.dao.AddressDao;
import com.kgc.entity.Address;
import com.kgc.entity.Message;
import com.kgc.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private AddressDao addressDao;
    @Override
    public Message getAddressListByUserId(Integer userId) {
        List<Address> addressListByUserId = addressDao.getAddressListByUserId(userId);
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
        Integer count = addressDao.addAddress(address);
        if (count != 1) {
            return Message.error();
        }
        return Message.success();
    }
}
