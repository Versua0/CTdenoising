package com.common.ctdenoising.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.ctdenoising.entity.fileState;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileStateMapper extends BaseMapper<fileState> {
}
