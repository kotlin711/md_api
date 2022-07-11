package org.example.api.model.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("md_login")
@Data
public class MdLogin {
    @TableId(type = IdType.NONE)
    private Integer uid;
    private String token;
}
