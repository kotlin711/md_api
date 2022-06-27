package org.example.api.model.entity;

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

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String title;

    private String content;

    private String link;

    private String version;

    private String deviceId;

    private Integer vipType;

    private String username;

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
