package com.common.ctdenoising.service.Impl;


import com.common.ctdenoising.entity.Files;
import com.common.ctdenoising.mapper.AfterFileMapper;
import com.common.ctdenoising.mapper.FileMapper;
import com.common.ctdenoising.response.ResponseCode;
import com.common.ctdenoising.response.Result;
import com.common.ctdenoising.service.FileService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileServiceImpl implements FileService {


    @Value("${file.save-path}")
    private String savePath;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private AfterFileMapper afterFileMapper;

    @Override
    public Result upLoadFiles(MultipartFile file) {
        long MAX_SIZE = 2097152L;
        // TODO: 需要保存用户的用于身份证明的信息或者说上传的时候返回图片的id，后续使用get方法下载该id处理完后的图片
        String fileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            return new Result(ResponseCode.FILE_NAME_EMPTY.getCode(), ResponseCode.FILE_NAME_EMPTY.getMsg(), null);
        }
        if (file.getSize() > MAX_SIZE) {
            return new Result(ResponseCode.FILE_MAX_SIZE.getCode(), ResponseCode.FILE_MAX_SIZE.getMsg(), null);
        }
        String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String newName = System.currentTimeMillis() + suffixName;
        File newFile = new File(savePath, newName);
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
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "数据上传成功");
    }

    @Override
    public Files getFileById(String id) {
        return fileMapper.selectById(id);

    }

    @Override
    public InputStream getFileInputStream(Files files) {
        File file = new File(files.getFilePath());
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
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

