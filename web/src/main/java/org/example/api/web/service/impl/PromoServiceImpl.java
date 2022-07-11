package org.example.api.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.api.model.entity.Promo;
import org.example.api.web.mapper.PromoMapper;
import org.example.api.web.service.IPromoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
@Service
public class PromoServiceImpl extends ServiceImpl<PromoMapper, Promo> implements IPromoService {

}
