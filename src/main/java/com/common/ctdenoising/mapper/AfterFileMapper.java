package com.common.ctdenoising.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;

import com.common.ctdenoising.entity.AfterFiles;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface AfterFileMapper extends BaseMapper<AfterFiles> {
    @Select("select * from afterfile")
    List<AfterFiles> findAll();
}
