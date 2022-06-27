package org.example.api.web.service.impl;

import org.example.api.model.entity.Notice;
import org.example.api.web.mapper.NoticeMapper;
import org.example.api.web.service.INoticeService;
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
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {

}
