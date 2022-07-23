package org.example.api.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.api.model.dto.GoodsDto;
import org.example.api.model.entity.Provide;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface IProvideService extends IService<Provide> {


    List<GoodsDto> page(Long page, Long limit);
}
