/*
+--------------------------------------------------------------------------
|   Mblog [#RELEASE_VERSION#]
|   ========================================
|   Copyright (c) 2014, 2019 mtons. All Rights Reserved
|   http://www.mtons.com
|
+---------------------------------------------------------------------------
*/
package com.asule.blog.base.storage.impl;


import com.asule.blog.base.lang.AsuleException;
import com.asule.blog.base.storage.Storage;
import com.asule.blog.base.utils.FileKit;
import com.asule.blog.base.utils.FilePathUtils;
import com.asule.blog.base.utils.ImageUtils;
import com.asule.blog.base.utils.MD5;
import com.asule.blog.config.SiteOptions;
import com.asule.blog.modules.po.Resource;
import com.asule.blog.modules.repository.ResourceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
public abstract class AbstractStorage implements Storage {


    @Autowired
    protected SiteOptions options;

    @Autowired
    protected ResourceRepository resourceRepository;

    protected void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new AsuleException("文件不能为空");
        }

        if (!FileKit.checkFileType(file.getOriginalFilename())) {
            throw new AsuleException("文件格式不支持");
        }
    }

    @Override
    public String store(MultipartFile file, String basePath) throws Exception {
        validateFile(file);
        return writeToStore(file.getBytes(), basePath, file.getOriginalFilename());
    }

    @Override
    public String storeScale(MultipartFile file, String basePath, int maxWidth) throws Exception {
        validateFile(file);
        //根据最大宽度压缩图片，返回该文件的二进制数组
        byte[] bytes = ImageUtils.scaleByWidth(file, maxWidth);
        return writeToStore(bytes, basePath, file.getOriginalFilename());
    }

    @Override
    public String storeScale(MultipartFile file, String basePath, int width, int height) throws Exception {
        validateFile(file);
        byte[] bytes = ImageUtils.screenshot(file, width, height);
        return writeToStore(bytes, basePath, file.getOriginalFilename());
    }

    public String writeToStore(byte[] bytes, String src, String originalFilename) throws Exception {
        String md5 = MD5.md5File(bytes);
        Resource resource = resourceRepository.findByMd5(md5);
        if (resource != null){
            return resource.getPath();
        }
        //根据计算的MD5，当作文件名

        String path = FilePathUtils.wholePathName(src, originalFilename, md5);
        path = writeToStore(bytes, path);

        // 图片入库
        resource = new Resource();
        resource.setMd5(md5);
        resource.setPath(path);
        resourceRepository.save(resource);
        return path;
    }

}
