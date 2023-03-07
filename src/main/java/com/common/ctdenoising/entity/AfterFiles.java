package com.common.ctdenoising.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.*;

import java.io.File;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@TableName(value = "afterFile")
public class AfterFiles implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 文件id
     */
    @TableId(value="id",type = IdType.AUTO)
    private Integer id;
    /**
     * 文件存储路径
     */
    private String filePath;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件后缀名
     */
    private String fileSuffix;



}
