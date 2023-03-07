package com.common.ctdenoising.service;


import com.common.ctdenoising.entity.Files;
import com.common.ctdenoising.response.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;


public interface FileService {

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    Result upLoadFiles(MultipartFile file);

    /**
     * 根据id获取文件
     * @param id
     * @return
     */
    Files getFileById(String id);

    /**
     * 根据id获取数据流
     * @param files
     * @return
     */
    InputStream getFileInputStream(Files files);



    /**
     * 对文件进行处理
     * @param
     * @return: 返回处理完的文件的id
     */
    List<Integer>  processFiles(List<Files> ids);

}
