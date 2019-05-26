package com.asule.blog.base.storage;

import org.springframework.web.multipart.MultipartFile;

public interface Storage {

    String store(MultipartFile file, String basePath) throws Exception;

    String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception;

    String storeScale(MultipartFile file, String basePath, int width, int height) throws Exception;

    void deleteFile(String storePath);

    String writeToStore(byte[] bytes, String pathAndFileName) throws Exception;

}
