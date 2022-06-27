package org.example.api.model.entity;

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
    private Integer id;
    @Schema(description = "促销标题")

    private String promoTitle;
    @Schema(description = "订单号")

    private String orderId;
    @Schema(description = "支付方式")

    private Integer payType;
    @Schema(description = "支付金额")

    private Float provideAmount;
    @Schema(description = "商品名称")

    private String provideName;
    @Schema(description = "支付结果")

    private Integer results;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(hidden = true)

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    @Schema(hidden = true)

    private Integer uid;


}
