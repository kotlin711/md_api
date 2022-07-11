package org.example.api.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.api.model.dto.OrderDto;
import org.example.api.model.entity.Order;
import org.example.api.model.req.OrderSearch;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface OrderMapper extends BaseMapper<Order> {
    List<OrderDto> page(OrderSearch orderSearch);
}
