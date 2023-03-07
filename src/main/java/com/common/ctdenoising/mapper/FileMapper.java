package com.common.ctdenoising.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.ctdenoising.entity.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface FileMapper extends BaseMapper<Files> {
    @Select("select * from file")
    List<Files>findAll();

}
