package org.example.api.web.mapper;

import org.apache.ibatis.annotations.Select;
import org.example.api.model.entity.Promo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface PromoMapper extends BaseMapper<Promo> {

    @Select("select  if(count(*)>0,true,false) from md_promo where pid=#{pid}")
    boolean does_it_exist(String pid);
}
