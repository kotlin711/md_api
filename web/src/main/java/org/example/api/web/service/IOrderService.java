package org.example.api.web.service;

import org.example.api.model.dto.OrderDto;
import org.example.api.model.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.api.model.req.OrderSearch;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface IOrderService extends IService<Order> {
     List<OrderDto> page(OrderSearch orderSearch) ;
}
