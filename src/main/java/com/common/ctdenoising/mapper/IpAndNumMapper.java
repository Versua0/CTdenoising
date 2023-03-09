package com.common.ctdenoising.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.ctdenoising.entity.Files;
import com.common.ctdenoising.entity.IpAndNum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface IpAndNumMapper extends BaseMapper<Files> {
    @Select("select * from ipandnum where ipAddr=#{ip}")
    IpAndNum getByIpAddr(@Param("ip") String ip);

}
