package com.common.ctdenoising.service.Impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.common.ctdenoising.entity.FileState;
import com.common.ctdenoising.mapper.FileStateMapper;
import com.common.ctdenoising.response.Result;
import com.common.ctdenoising.service.FileStateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public  class FileStateServiceImpl extends ServiceImpl<FileStateMapper, FileState>implements FileStateService {

}
