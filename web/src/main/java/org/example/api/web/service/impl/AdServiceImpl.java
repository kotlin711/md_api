package org.example.api.web.service.impl;

import org.example.api.model.entity.Ad;
import org.example.api.web.mapper.AdMapper;
import org.example.api.web.service.IAdService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
@Service
public class AdServiceImpl extends ServiceImpl<AdMapper, Ad> implements IAdService {

}
