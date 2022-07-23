package org.example.api.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.api.model.dto.GoodsDto;
import org.example.api.model.entity.Provide;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface ProvideMapper extends BaseMapper<Provide> {

    List<GoodsDto> query_goods(Long page,Long limit);
}
