package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: mmall
 * @description: 文件处理的服务
 * @author: Mr.Tang
 * @create: 2018-09-27 00:02
 **/
public interface IFileService {

    // 文件上传
    String upload(MultipartFile file, String path);
}
