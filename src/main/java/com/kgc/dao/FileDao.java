package com.kgc.dao;

import com.kgc.entity.File;

public interface FileDao {
    int addFile(String filePath);
    File getFileIdByPicPath(String picPath);

    int modifyProIdById(File file);
}
