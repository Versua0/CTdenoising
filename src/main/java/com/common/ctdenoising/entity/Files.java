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
@TableName(value = "file")
public class Files implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 文件id
     */
    @TableId(value="id",type = IdType.AUTO)
    private  Integer id;
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

    /**
     * 将相同的两个类进行转换
     * @param file
     * @return: com.common.ctdenoising.entity.AfterFiles
     */
    public AfterFiles ConvertTo(Files file){
        AfterFiles afterFiles=new AfterFiles();
        afterFiles.setId(file.getId());
        afterFiles.setFilePath(file.getFilePath());
        afterFiles.setFileName(file.getFileName());
        afterFiles.setFileSuffix(file.getFileSuffix());
        return afterFiles;


    }
}
