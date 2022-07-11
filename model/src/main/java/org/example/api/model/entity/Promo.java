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
@TableName("md_promo")
public class Promo implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "商品ID",index = 1)
    @ColumnWidth(value = 20)
    private Integer pid;
    @ExcelProperty(value = "标题",index = 2)
    @ColumnWidth(value = 20)
    private String title;
    @ExcelProperty(value = "内容",index = 3)
    @ColumnWidth(value = 20)
    private String content;
    @ExcelProperty(value = "商品名称",index = 4)
    @ColumnWidth(value = 20)
    private String provideName;
    @ExcelProperty(value = "打折金额",index = 5)
    @ColumnWidth(value = 20)
    private Float promoAmount;
    @ExcelProperty(value = "商品金额",index = 6)
    @ColumnWidth(value = 20)
    private Float provideAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "开始时间",index = 7)
    @ColumnWidth(value = 20)
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "结束时间",index = 8)
    @ColumnWidth(value = 20)
    private LocalDateTime endTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
