package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Address;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.AddressService;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("getAddressListByUserId")
    public Message getAddressListByUserId(){
        User user = ThreadLocalUtil.get();
        if (user == null) {
            return Message.error();
        }
        return addressService.getAddressListByUserId(user.getId());
    }
    @GetMapping("getAddressById")
    public Message getAddressById(String id){
        if (id == null || "".equals(id)) {
            return Message.error();
        }
        int idInt = Integer.parseInt(id);
        return addressService.getAddressListByUserId(idInt);
    }
    @PostMapping("addAddress")
    public Message addAddress(@RequestBody Map map){
        User user = ThreadLocalUtil.get();
        Object addressObj = map.get("address");
        Address address = JSON.parseObject(JSON.toJSONString(addressObj),Address.class);
        if (address == null) {
            return Message.error();
        }
        address.setUserId(user.getId());
        return addressService.addAddress(address);
    }
}
