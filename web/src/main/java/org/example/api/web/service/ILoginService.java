package org.example.api.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.api.model.entity.Admin;
import org.example.api.model.entity.MdLogin;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author crying711
 * @since 2022-06-27
 */
public interface ILoginService extends IService<MdLogin> {
    boolean  is_login(String token);
    void   save_data(Integer uid,String token);

}
