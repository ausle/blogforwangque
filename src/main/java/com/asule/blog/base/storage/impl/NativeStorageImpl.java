package com.asule.blog.base.storage.impl;

import com.asule.blog.base.storage.Storage;
import com.asule.blog.base.utils.FileKit;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class NativeStorageImpl extends AbstractStorage{

    @Override
    public void deleteFile(String storePath) {

    }

    @Override
    public String writeToStore(byte[] bytes, String pathAndFileName) throws Exception {
        String dest = getStoragePath() + pathAndFileName;
        FileKit.writeByteArrayToFile(bytes, dest);
        return pathAndFileName;
    }

    //加上全路径
    private String getStoragePath() {
        return options.getLocation();
    }
}
