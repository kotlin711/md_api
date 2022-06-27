package org.example.api.web.service.impl;

import org.example.api.model.entity.MdFile;
import org.example.api.web.mapper.FileMapper;
import org.example.api.web.service.IFileService;
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
public class FileServiceImpl extends ServiceImpl<FileMapper, MdFile> implements IFileService {

}
