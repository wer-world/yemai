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
}
