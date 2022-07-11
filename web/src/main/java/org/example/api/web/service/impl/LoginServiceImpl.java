package org.example.api.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.api.model.entity.Admin;
import org.example.api.model.entity.MdLogin;
import org.example.api.web.mapper.AdminMapper;
import org.example.api.web.mapper.LoginMapper;
import org.example.api.web.service.IAdminService;
import org.example.api.web.service.ILoginService;
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
public class LoginServiceImpl extends ServiceImpl<LoginMapper, MdLogin> implements ILoginService {

    @Override
    public boolean is_login( String token) {
        LambdaQueryWrapper<MdLogin> mdLoginLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mdLoginLambdaQueryWrapper.eq(MdLogin::getToken,token);
        return getOne(mdLoginLambdaQueryWrapper) != null;
    }

    @Override
    public void save_data(Integer uid, String token) {


        MdLogin mdLogin = new MdLogin();
        mdLogin.setToken(token);
        mdLogin.setUid(uid);
        if (getById(uid) != null) {
            updateById(mdLogin);
        }else {
            save(mdLogin);
        }



    }
}
