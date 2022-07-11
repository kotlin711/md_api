package org.example.api.model.entity;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("md_app")
public class MdApp implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "版本号",index = 1)
    @ColumnWidth(value = 20)
    private String version;
    @ExcelProperty(value = "MD5",index = 2)
    @ColumnWidth(value = 20)
    private String md5;
    @ExcelProperty(value = "SIZE",index = 3)
    @ColumnWidth(value = 20)
    private Integer size;
    @ExcelProperty(value = "创建时间",index = 4)
    @ColumnWidth(value = 20)
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
