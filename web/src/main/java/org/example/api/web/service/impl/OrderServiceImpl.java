package org.example.api.web.service.impl;

import org.example.api.model.dto.OrderDto;
import org.example.api.model.entity.Order;
import org.example.api.model.req.OrderSearch;
import org.example.api.web.mapper.OrderMapper;
import org.example.api.web.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public List<OrderDto> page(OrderSearch orderSearch) {
         orderSearch.setPage( orderSearch.getLimit() * (orderSearch.getPage() - 1));
        return baseMapper.page(orderSearch);
    }
}
