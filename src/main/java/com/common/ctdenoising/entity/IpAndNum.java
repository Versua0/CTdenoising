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
@TableName(value = "ipandNum")
public class IpAndNum implements Serializable {

    private static final long serialVersionUID=1L;
    /**
     * 文件id
     */
    @TableId(value="id",type = IdType.AUTO)
    private  Integer id;
    /**
     * 文件存储路径
     */
    private String IpAddr;
    /**
     * 文件名称
     */
    private Integer picNum;
}
