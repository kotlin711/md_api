package org.example.api.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.api.model.dto.GoodsDto;
import org.example.api.model.entity.Provide;
import org.example.api.web.mapper.ProvideMapper;
import org.example.api.web.service.IProvideService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
@Service
public class ProvideServiceImpl extends ServiceImpl<ProvideMapper, Provide> implements IProvideService {
    @Override
    public List<GoodsDto> page(Long page, Long limit) {
        page = limit * (page - 1);
        return baseMapper.query_goods(page, limit);
    }
}
