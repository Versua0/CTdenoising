package com.common.ctdenoising.Thread;

import com.common.ctdenoising.entity.Files;
import com.common.ctdenoising.entity.AfterFiles;
import com.common.ctdenoising.mapper.AfterFileMapper;
import com.common.ctdenoising.mapper.FileMapper;
import com.common.ctdenoising.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MonitorFilesThread extends Thread{

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private AfterFileMapper AfterFileMapper;
    @Autowired
    private FileService fileService;

    private final String name;

    public MonitorFilesThread(String name){
        this.name = name;
    }

    /**
     * 在系统运行的时候保持该线程的循环运行
     * 用于监控是否有新的需要运算的图片
     * 如果有：1. 调用python代码进行于运算
     *       2. 将图片信息从数据库的两个表中进行转移(同时修改文件的路径)
     *       3. 等待用户端进行get请求，再发回文件
     */
    @Override
    public void run() {
       while(true){
           List<Files> filesList=fileMapper.findAll();
           if(!filesList.isEmpty()){  //有文件需要处理

              List<Integer> ids=fileService.processFiles(filesList);

           //从A表->B表
           //从A表删除
           Integer result =fileMapper.deleteBatchIds(ids);
           if(result!=null){
               for (Files file:filesList ) {
                    AfterFiles afterFiles=file.ConvertTo(file);
                    //TODO: 需要对文件路径进行修改，未处理的图片跟处理后的文件存储的地方不同
                    AfterFileMapper.insert(afterFiles);
               }

           }
           }
       }
    }


}
