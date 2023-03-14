package com.common.ctdenoising.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.ctdenoising.entity.FileState;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileStateMapper extends BaseMapper<FileState> {
   @Select("select * from filestate where ipAddr=#{ip,jdbcType=VARCHAR}")
    FileState selectByIp(@Param("ip") String ip);
}
