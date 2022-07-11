package org.example.api.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("md_gift")
public class Gift implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "商品名称",index = 1)
    @ColumnWidth(value = 20)
    private Integer provideName;

    @ExcelProperty(value = "KEY",index = 2)
    @ColumnWidth(value = 20)
    @TableField(value = "`key`")
    private String key;
    @ExcelProperty(value = "状态",index = 3)
    @ColumnWidth(value = 20)
    private Integer state;
    @ExcelProperty(value = "使用者",index = 4)
    @ColumnWidth(value = 20)
    private String username;
    @ExcelProperty(value = "创建时间",index = 5)
    @ColumnWidth(value = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
