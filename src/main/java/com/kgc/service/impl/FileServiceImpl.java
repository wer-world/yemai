package com.kgc.service.impl;

import com.kgc.dao.FileDao;
import com.kgc.entity.Message;
import com.kgc.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;
    @Override
    public Message upload(MultipartFile multipartFile) {
        String oriFileName = null;
        String fileExtention = null;
        String newFilePath = null;
        if (multipartFile != null) {
            oriFileName = multipartFile.getOriginalFilename();
            fileExtention = FilenameUtils.getExtension(oriFileName);
            if (multipartFile.getSize() > 2000000) {
                return Message.error("上传的文件不能超过2MB");
            } else if (!fileExtention.equalsIgnoreCase("jpg")
                    && !fileExtention.equalsIgnoreCase("jpeg")
                    && !fileExtention.equalsIgnoreCase("png")
                    && !fileExtention.equalsIgnoreCase("pneg")) {
                return Message.error("上传格式只能为jpg/png/jpeg/pneg");
            }
            newFilePath = "D:" + File.separator + "img" + File.separator + UUID.randomUUID().toString() + fileExtention;
            try {
                multipartFile.transferTo(new File(newFilePath));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return Message.success(newFilePath);
        }
        return Message.error();
    }

    @Override
    public Message addFile(String filePath) {
        if (filePath == null || "".equals(filePath)) {
            return Message.error();
        }
        int count = fileDao.addFile(filePath);
        if (count == 0) {
            return Message.error();
        }
        return Message.success(count);
    }

    @Override
    public Message getFileIdByPicPath(String picPath) {
        if (picPath == null || "".equals(picPath)) {
            return Message.error();
        }
        com.kgc.entity.File fileIdByPicPath = fileDao.getFileIdByPicPath(picPath);
        if (fileIdByPicPath != null) {
            return Message.success(fileIdByPicPath);
        }
        return Message.error();
    }

    @Override
    public Message modifyProIdById(com.kgc.entity.File file) {
        int count = fileDao.modifyProIdById(file);
        if (count != 1) {
            return Message.error();
        }
        return Message.success();
    }
}
