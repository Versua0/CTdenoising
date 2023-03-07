package com.common.ctdenoising.Thread;

import com.common.ctdenoising.entity.AfterFiles;
import com.common.ctdenoising.mapper.AfterFileMapper;
import com.common.ctdenoising.mapper.FileMapper;
import com.common.ctdenoising.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MonitorAfterFilesThread extends Thread{

    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private AfterFileMapper afterFileMapper;
    @Autowired
    private FileService fileService;

    private final String name;

    public MonitorAfterFilesThread(String name){
        this.name = name;
    }

    /**
     * 这就是线程执行的逻辑体
     */
    @Override
    public void run() {
       while(true){
           List<AfterFiles> filesList=afterFileMapper.findAll();
           // TODO: 文件的返回
       }
    }


}
