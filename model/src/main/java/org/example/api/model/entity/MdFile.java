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
@TableName("md_file")
public class MdFile implements Serializable {

    private static final long serialVersionUID = 1L;
    @ExcelProperty(value = "ID",index = 0)
    @ColumnWidth(value = 20)
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ExcelProperty(value = "保存模式",index = 1)
    @ColumnWidth(value = 20)
    private Integer saveModel;
    @ExcelProperty(value = "类型",index = 2)
    @ColumnWidth(value = 20)
    private Integer type;
    @ExcelProperty(value = "名称",index = 3)
    @ColumnWidth(value = 20)
    private String name;
    @ExcelProperty(value = "链接",index = 4)
    @ColumnWidth(value = 20)
    private String link;
    @ExcelProperty(value = "大小",index = 5)
    @ColumnWidth(value = 20)
    private Long size;


}
