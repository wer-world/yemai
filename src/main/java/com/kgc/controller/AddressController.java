package com.kgc.controller;

import com.alibaba.fastjson.JSON;
import com.kgc.entity.Address;
import com.kgc.entity.Message;
import com.kgc.entity.User;
import com.kgc.service.AddressService;
import com.kgc.util.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @GetMapping("getAddressListByUserId")
    public Message getAddressListByUserId(String currentPage,String pageSize){
        Map<String,Object> map = new HashMap<>();
        if (currentPage == null || currentPage.isEmpty()) {
            return Message.error();
        }
        int currentPageInt = Integer.parseInt(currentPage);
        if (pageSize == null || pageSize.isEmpty()) {
            return Message.error();
        }
        int pageSizeInt = Integer.parseInt(pageSize);
        int form = (currentPageInt-1)*pageSizeInt;
        User user = ThreadLocalUtil.get();
        if (user == null) {
            return Message.error();
        }
        map.put("userId",user.getId());
        map.put("form",form);
        map.put("pageSize",pageSizeInt);
        return addressService.getAddressListByUserId(map);
    }
    @GetMapping("getAddressById")
    public Message getAddressById(String id){
        if (id == null || "".equals(id)) {
            return Message.error();
        }
        int idInt = Integer.parseInt(id);
        return addressService.getAddressById(idInt);
    }
    @PostMapping("addAddress")
    public Message addAddress(@RequestBody Map map){
        User user = ThreadLocalUtil.get();
        if (user == null) {
            return Message.error();
        }
        Object addressObj = map.get("address");
        Address address = JSON.parseObject(JSON.toJSONString(addressObj),Address.class);
        if (address == null) {
            return Message.error();
        }
        address.setUserId(user.getId());
        return addressService.addAddress(address);
    }

    @GetMapping("deleteAddressById")
    public Message deleteAddressById(String id){
        if (id == null || "".equals(id)) {
            return Message.error();
        }
        int idInt = Integer.parseInt(id);
        return addressService.deleteAddressById(idInt);
    }

    @PostMapping("modifyAddressById")
    public Message modifyAddressById(@RequestBody Map map){
        Object addressObj = map.get("address");
        Address address = JSON.parseObject(JSON.toJSONString(addressObj),Address.class);
        if (address == null){
            return  Message.error();
        }
        return addressService.modifyAddressById(address);
    }

    @GetMapping("getAddressCountByUserId")
    public Message getAddressCountByUserId(){
        User user = ThreadLocalUtil.get();
        if (user == null) {
            return Message.error();
        }
        return addressService.getAddressCountByUserId(user.getId());
    }
}
