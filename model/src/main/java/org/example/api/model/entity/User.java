package org.example.api.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName("md_user")
@Schema(name = "用户类")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    private Integer id;
    @ExcelProperty(value = "姓名",index = 1)
    @ColumnWidth(value = 20)
    @Schema(name = "username",description = "姓名")
    private String username;
    @ExcelProperty(value = "密码",index = 2)
    @ColumnWidth(value = 20)
    @Schema(name = "pwd",description = "密码")
    @JsonIgnore
    private String pwd;
    @ExcelProperty(value = "头像",index = 3)
    @ColumnWidth(value = 20)
    private String avatar;
    @Schema(name = "email",description = "邮箱")
    @ExcelProperty(value = "邮箱",index = 4)
    @ColumnWidth(value = 20)
    private String email;
    @ExcelProperty(value = "VIP类型",index = 5)
    @ColumnWidth(value = 20)
    private Integer vip;
    @ExcelProperty(value = "登录时间",index = 6)
    @ColumnWidth(value = 20)
    private    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lastLoginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "注册时间",index = 7)
    @ColumnWidth(value = 20)
    @TableField(fill =  FieldFill.INSERT)
    private LocalDateTime regTime;
    @ExcelProperty(value = "状态",index = 8)
    @ColumnWidth(value = 20)
    private Integer state;
    @ExcelProperty(value = "设备ID",index = 9)
    @ColumnWidth(value = 20)
    private String deviceId;
    @ExcelProperty(value = "VIP过期时间",index = 10)
    @ColumnWidth(value = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime vieEndTime;


}
