package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @program: mmall
 * @description: 文件上传服务实现类
 * @author: Mr.Tang
 * @create: 2018-09-27 00:03
 **/
@Service("iFileService")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file, String path) {

        // 原始文件名
        String fileName = file.getOriginalFilename();

        // 获取扩展名 +1 是为了不要.
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);

        // 上传文件的名字 UUID使每次上传图片文件名不一样
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;

        logger.info("开始上传文件,上传文件的文件名：{},上传的路径：{},新文件名：{}", fileName, path, uploadFileName);

        File fileDir = new File(path);
        if (!fileDir.exists()) {
            // 判断目录是否存在
            fileDir.setWritable(true); // 使它有写的权限
            fileDir.mkdirs();
        }

        // 创建文件
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            // 文件上传成功

            // tod  将targetFile上传到FTP服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // 已经上传到FTP服务器上

            // tod  上传完之后,删除upload下面的文件
            targetFile.delete();

        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }

        return targetFile.getName();
    }
}
