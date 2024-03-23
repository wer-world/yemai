package com.kgc.controller;

import com.kgc.entity.Message;
import com.kgc.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileService fileService;
    @RequestMapping("addFile")
    public Message addFile( @RequestParam (value = "file",required = false)MultipartFile multipartFile){
        Message upload = fileService.upload(multipartFile);
        if ("200".equals(upload.getCode())) {
            return fileService.addFile((String) upload.getData());
        }
        return Message.error();
    }
}
