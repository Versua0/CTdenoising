package com.common.ctdenoising.service.Impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.ctdenoising.entity.Files;
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
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements FileService {


    @Value("${file.save-path}")
    private String savePath;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private FileStateService fileStateService;
    @Autowired
    private RestTemplateMethods restTemplateMethods;

    public Result upLoadFiles(MultipartFile file, HttpServletRequest request) {
        long MAX_SIZE = 2097152L;
        // 通过 System.getProperty("user.dir") 方式获取到项目根目录
        String projectRootDirectoryPath = System.getProperty("user.dir");
        // 通过 File 对象的 getParent() 方法获取到根目录的上级目录
        String parentPath = new File(projectRootDirectoryPath).getParent();
        String path = parentPath + savePath; //python 项目的未处理文件路径

        String fileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            return new Result(ResponseCode.FILE_NAME_EMPTY.getCode(), ResponseCode.FILE_NAME_EMPTY.getMsg(), null);
        }
        if (file.getSize() > MAX_SIZE) {
            return new Result(ResponseCode.FILE_MAX_SIZE.getCode(), ResponseCode.FILE_MAX_SIZE.getMsg(), null);
        }
        String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String saveChildPath = path + "/" + IpUtil.getClientIpAddr(request);
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
        Files files = new Files(null, newFile.getPath(), fileName, suffixName);
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


    public Result processFiles(HttpServletRequest request) {
        //对该ip文件夹下的文件进行处理
        restTemplateMethods.RestTemplatePost(IpUtil.getClientIpAddr(request));
        //对原始文件进行删除

        String projectRootDirectoryPath = System.getProperty("user.dir");
        // 通过 File 对象的 getParent() 方法获取到根目录的上级目录
        String parentPath = new File(projectRootDirectoryPath).getParent();
        String filesPath = parentPath + savePath + '/' + IpUtil.getClientIpAddr(request);
        System.out.println(filesPath);
        //从结果文件夹发回--这个可以用一个get来请求或者使用轮询
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "OK");
    }

    @Override
    public Result returnUrls(HttpServletRequest request, HttpServletResponse response) {
        // 通过 System.getProperty("user.dir") 方式获取到项目根目录
        String projectRootDirectoryPath = System.getProperty("user.dir");
        // 通过 File 对象的 getParent() 方法获取到根目录的上级目录
        String parentPath = new File(projectRootDirectoryPath).getParent();
        //结果图片存放位置
        String resultPath = "/RED-CNN-master(Lite)/save/fig/";
        String pppath = "wadouri:http://localhost:80";//不能访问本地文件，换成映射路径
        String ip = IpUtil.getClientIpAddr(request);
        String path = parentPath + resultPath + ip; //python 图片目录
        String RetPath = pppath + "/result/"+ ip + "/";
        File file = new File(path);
        File[] files = file.listFiles(); //获取所有的文件名
        List<String> ResultUrls = new ArrayList<>();
        for (File file1 : files) {
            ResultUrls.add(RetPath + file1.getName());
        }
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), ResultUrls);
    }

}
