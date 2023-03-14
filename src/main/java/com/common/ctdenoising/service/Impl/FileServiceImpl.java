package com.common.ctdenoising.service.Impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.ctdenoising.entity.Files;
import com.common.ctdenoising.mapper.AfterFileMapper;
import com.common.ctdenoising.mapper.FileMapper;
import com.common.ctdenoising.response.ResponseCode;
import com.common.ctdenoising.response.Result;
import com.common.ctdenoising.service.FileService;
import com.common.ctdenoising.service.FileStateService;
import com.common.ctdenoising.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public  class FileServiceImpl extends ServiceImpl<FileMapper,Files> implements FileService {


    @Value("${file.save-path}")
    private String savePath;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileStateService fileStateService;

    public Result upLoadFiles(MultipartFile file, HttpServletRequest request) {
        long MAX_SIZE = 2097152L;
        String fileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            return new Result(ResponseCode.FILE_NAME_EMPTY.getCode(), ResponseCode.FILE_NAME_EMPTY.getMsg(), null);
        }
        if (file.getSize() > MAX_SIZE) {
            return new Result(ResponseCode.FILE_MAX_SIZE.getCode(), ResponseCode.FILE_MAX_SIZE.getMsg(), null);
        }
        String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String saveChildPath=savePath+ "/"+IpUtil.getClientIpAddr(request);
        File newFile = new File(saveChildPath, fileName);
        if (!newFile.getParentFile().exists()) {
            newFile.getParentFile().mkdirs();
        }
        try {
            //文件写入--文件的转存
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Files files = new Files(null,newFile.getPath(), fileName, suffixName);
        fileMapper.insert(files);
        fileStateService.IfCreateStateWithIp(IpUtil.getClientIpAddr(request));
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "数据上传成功");
    }

    public Files getFileById(String id) {
        return fileMapper.selectById(id);

    }

    public InputStream getFileInputStream(Files files) {
        File file = new File(files.getFilePath());
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Integer> processFiles(List<Files> filesList) {
        List<Integer> ids=new ArrayList<>();
        for (Files file : filesList) {
            // 对未处理的文件进行迭代，
            int FId = file.getId();
            ids.add(FId);

        }
        return ids;
    }
}

