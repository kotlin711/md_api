package org.example.api.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.example.api.model.entity.Admin;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author crying711
 * @since 2022-06-27
 */
public interface AdminMapper extends BaseMapper<Admin> {


    @Select("select  if(count(*)>0,true,false) from md_admin where id=#{id} and password=#{pwd};")
    boolean login(String id, String pwd);
}
