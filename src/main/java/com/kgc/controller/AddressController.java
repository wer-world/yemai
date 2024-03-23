package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @RequestMapping("getAddressListByUserId")
    public Message getAddressListByUserId(String userId){
        if (userId == null || "".equals(userId)) {
            return Message.error();
        }
        int userIdInt = Integer.parseInt(userId);
        return addressService.getAddressListByUserId(userIdInt);
    }
}
