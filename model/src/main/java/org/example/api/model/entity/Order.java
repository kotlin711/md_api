package org.example.api.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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
@TableName("md_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(hidden = true)
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    private Integer id;
    @Schema(description = "促销标题")
    @ExcelProperty(value = "促销标题",index = 1)
    @ColumnWidth(value = 20)
    private String promoTitle;
    @Schema(description = "订单号")
    @ExcelProperty(value = "订单号",index = 2)
    @ColumnWidth(value = 20)
    private String orderId;
    @Schema(description = "支付方式")
    @ExcelProperty(value = "支付方式",index = 3)
    @ColumnWidth(value = 20)
    private Integer payType;
    @Schema(description = "支付金额")
    @ExcelProperty(value = "支付金额",index = 4)
    @ColumnWidth(value = 20)
    private Float provideAmount;
    @Schema(description = "商品名称")
    @ExcelProperty(value = "商品名称",index = 5)
    @ColumnWidth(value = 20)
    private String provideName;
    @Schema(description = "支付结果")
    @ExcelProperty(value = "支付结果",index = 6)
    @ColumnWidth(value = 20)
    private Integer results;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(hidden = true)
    @ExcelProperty(value = "创建时间",index = 7)
    @ColumnWidth(value = 20)
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @Schema(hidden = true)
    @ExcelProperty(value = "用户ID",index = 8)
    @ColumnWidth(value = 20)
    private Integer uid;


}
