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
@TableName("md_provide")
public class Provide implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "商品名",index = 1)
    @ColumnWidth(value = 20)
    private String name;
    @ExcelProperty(value = "价格",index = 2)
    @ColumnWidth(value = 20)
    private Float amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间",index = 3)
    @ColumnWidth(value = 20)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
