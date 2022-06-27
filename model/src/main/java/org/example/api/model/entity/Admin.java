package org.example.api.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author crying711
 * @since 2022-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("md_admin")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String password;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


}
