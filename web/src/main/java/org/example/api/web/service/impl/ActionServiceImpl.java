package org.example.api.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.api.model.entity.Action;
import org.example.api.model.entity.Admin;
import org.example.api.web.mapper.ActionMapper;
import org.example.api.web.mapper.AdminMapper;
import org.example.api.web.service.IActionService;
import org.example.api.web.service.IAdminService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author crying711
 * @since 2022-06-27
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements IActionService {

}
