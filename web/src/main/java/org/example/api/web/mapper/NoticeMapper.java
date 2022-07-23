package org.example.api.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.example.api.model.dto.NoticeDto;
import org.example.api.model.entity.Notice;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
public interface NoticeMapper extends BaseMapper<Notice> {


   List<NoticeDto> query_notice();


}
