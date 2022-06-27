package org.example.api.web.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.api.model.entity.Gift;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface GiftMapper extends BaseMapper<Gift> {



    @Update("update md_gift set state = 1,username=(select * from md_user where md_user.id=#{uid}) where `key`=#{code};")
    boolean bind(String uid,String code);
    @Select("select  if(count(*)>0,false,true) from md_gift where `key`=#{code} and username=null;")
    boolean does_it_exist(String code);

    @Select("select provide_name from md_gift where  `key`=#{code};")
    Integer get_name(String code);
}
