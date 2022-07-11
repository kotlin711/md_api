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
@TableName("md_notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "标题",index = 1)
    @ColumnWidth(value = 20)
    private String title;
    @ExcelProperty(value = "内容",index = 2)
    @ColumnWidth(value = 20)
    private String content;
    @ExcelProperty(value = "链接",index = 3)
    @ColumnWidth(value = 20)
    private String link;
    @ExcelProperty(value = "版本",index = 4)
    @ColumnWidth(value = 20)
    private String version;
    @ExcelProperty(value = "设备ID",index = 5)
    @ColumnWidth(value = 20)
    private String deviceId;
    @ExcelProperty(value = "VIP类型",index = 6)
    @ColumnWidth(value = 20)
    private Integer vipType;
    @ExcelProperty(value = "用户名",index = 7)
    @ColumnWidth(value = 20)
    private String username;
    @ExcelProperty(value = "事件类型",index = 8)
    @ColumnWidth(value = 20)
    private Integer action;
    /*
        1.版本号码
        2.设备id
        3.指定会员等级
        4.指定用户名
        5.ALL_USER
        6.聚合操作
     */

    private Integer eventType;


}
