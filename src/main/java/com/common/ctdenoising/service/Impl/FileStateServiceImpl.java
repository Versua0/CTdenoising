package com.common.ctdenoising.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.ctdenoising.entity.FileState;
import com.common.ctdenoising.mapper.FileStateMapper;
import com.common.ctdenoising.response.ResponseCode;
import com.common.ctdenoising.response.Result;
import com.common.ctdenoising.service.FileStateService;
import com.common.ctdenoising.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public  class FileStateServiceImpl extends ServiceImpl<FileStateMapper, FileState>implements FileStateService {

    @Autowired
    private FileStateMapper fileStateMapper;

    @Override
    public Result UpdateUploadStateByIp(HttpServletRequest request) {
        String ip= IpUtil.getClientIpAddr(request);//获取ip
        //通过ip地址在表中查询实体
        FileState fileState=fileStateMapper.selectByIp(ip);
        System.out.println(fileState);
        if(fileState!=null) {
            //上传完毕设为1
            fileState.setUploadCompletedState(1);
            fileStateMapper.updateById(fileState);
            return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "状态修改成功");
        }
        return new Result(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getMsg(), "还未提交文件");
    }
    @Override
    public Result UpdateProcessingStateByIp(HttpServletRequest request) {
        String ip= IpUtil.getClientIpAddr(request);//获取ip
        //通过ip地址在表中查询实体
        FileState fileState=fileStateMapper.selectByIp(ip);
        if(fileState!=null) {
            //上传完毕设为1
            fileState.setProcessingCompletedState(1);
            fileStateMapper.updateById(fileState);
            return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "状态修改成功");
        }
        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "状态修改成功");
    }

    @Override
    public Result IfCreateStateWithIp(String ip) {
        //查询结果
        FileState fileState1=fileStateMapper.selectByIp(ip);
        if(fileState1==null){//没有则新建,直接使用上面用来查询的实例
            FileState fileState=new FileState();
            fileState.setIpAddr(ip);
            fileState.setProcessingCompletedState(0);
            fileState.setUploadCompletedState(0);
            //插入
            fileStateMapper.insert(fileState);
            return new Result(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getMsg(), "新建ip 状态成功");
        }

        return new Result(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMsg(), "已有该ip状态");
    }
}
