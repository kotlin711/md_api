package org.example.api.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@TableName("md_ad")
public class Ad implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "标题",index = 1)
    @ColumnWidth(value = 20)
    private String title;
    @ExcelProperty(value = "备注",index = 2)
    @ColumnWidth(value = 20)
    private String flag;
    @ExcelProperty(value = "类型",index = 3)
    @ColumnWidth(value = 20)
    private Integer type;
    @ExcelProperty(value = "链接",index = 4)
    @ColumnWidth(value = 20)
    private String link;
    @ExcelProperty(value = "图片",index = 5)
    @ColumnWidth(value = 20)
    private String pic;
    @ExcelProperty(value = "规则",index = 6)
    @ColumnWidth(value = 20)
    private String rrule;


}
