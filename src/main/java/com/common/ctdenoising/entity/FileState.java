package com.common.ctdenoising.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@TableName("fileState")
public class FileState implements Serializable {
    private static final long serialVersionUID=1L;
    /**
     * 文件id
     */
    @TableId(value="id",type = IdType.AUTO)
    private  Integer id;

    /**
     * ip地址
     */
    private String ipAddr;

    /**
     * 用来记录前端是否已上传完成的状态码
     */
    private Integer uploadCompletedState;
    /**
     * 用来记录文件已处理完成的状态码
     */
    private Integer processingCompletedState;
}
