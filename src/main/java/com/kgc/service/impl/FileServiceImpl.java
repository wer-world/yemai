package com.kgc.service.impl;

import com.kgc.config.FileConfig;
import com.kgc.dao.FileDao;
import com.kgc.entity.Message;
import com.kgc.enums.FileExceptionEnum;
import com.kgc.exception.ServiceException;
import com.kgc.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileDao fileDao;

    @Autowired
    private FileConfig fileConfig;

    @Override
    @Transactional
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
            newFilePath = fileConfig.getFilePath() + UUID.randomUUID().toString() + "." + fileExtention;
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
    @Transactional
    public Message addFile(String filePath) {
        Integer flag = fileDao.addFile(filePath);
        if (flag == 0) {
            throw new ServiceException("FileServiceImpl addFile " + FileExceptionEnum.FILE_ADD_FAILURE.getMessage(), FileExceptionEnum.FILE_ADD_FAILURE.getMsg());
        }
        return Message.success();
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
    @Transactional
    public Message modifyProIdById(com.kgc.entity.File file) {
        Integer flag = fileDao.modifyProIdById(file);
        if (flag == 0) {
            throw new ServiceException("FileServiceImpl addFile " + FileExceptionEnum.FILE_UPDATE_FAILURE.getMessage(), FileExceptionEnum.FILE_UPDATE_FAILURE.getMsg());
        }
        return Message.success();
    }
}
