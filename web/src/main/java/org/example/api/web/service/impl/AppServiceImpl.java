package org.example.api.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.api.model.entity.Ad;
import org.example.api.model.entity.MdApp;
import org.example.api.web.mapper.AdMapper;
import org.example.api.web.mapper.AppMapper;
import org.example.api.web.service.IAdService;
import org.example.api.web.service.IAppService;
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
public class AppServiceImpl extends ServiceImpl<AppMapper, MdApp> implements IAppService {

}
