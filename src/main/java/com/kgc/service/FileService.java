package com.kgc.service;

import com.kgc.entity.File;
import com.kgc.entity.Message;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传文件
     * @param multipartFile
     * @return
     */
    Message upload(MultipartFile multipartFile);

    /**
     * 添加文件
     * @param filePath
     * @return
     */
    Message addFile(String filePath);

    /**
     * 根据图片路径查到id
     * @param picPath
     * @return
     */
    Message getFileIdByPicPath(String picPath);

    Message modifyProIdById(File file);

    /**
     * 修改文件路径
     * @param file
     * @return
     */
    Message modifyPicPathById(File file);

    /**
     * 根据文件id获取文件路径
     * @param id
     * @return
     */
    Message getPicPathByFileId(Integer id);
}
