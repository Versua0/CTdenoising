package com.common.ctdenoising.service;

import com.baomidou.mybatisplus.service.IService;
import com.common.ctdenoising.entity.FileState;
import com.common.ctdenoising.response.Result;

import javax.servlet.http.HttpServletRequest;

public interface FileStateService extends IService<FileState> {
    /**
     * 通过发送请求的ip改变其状态
     * @param request
     */
    Result UpdateUploadStateByIp(HttpServletRequest request);
    /**
     * 通过发送请求的ip改变其状态
     * @param request
     */
    Result UpdateProcessingStateByIp(HttpServletRequest request);
    /**
     * 上传文件时查询是否有该ip关联的状态，没有则创建
     * @param ip
     * @return: com.common.ctdenoising.response.Result
     */
    Result IfCreateStateWithIp(String ip);
}
