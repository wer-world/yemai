package com.kgc.dao;

import com.kgc.entity.File;

public interface FileDao {
    Integer addFile(String filePath);
    File getFileIdByPicPath(String picPath);

    Integer modifyProIdById(File file);
}
