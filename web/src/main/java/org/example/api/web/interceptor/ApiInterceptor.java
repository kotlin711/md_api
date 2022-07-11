package org.example.api.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.enc.JwtUtils;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.LoginServiceImpl;
import org.example.api.web.service.impl.UserServiceImpl;
import org.example.api.web.util.ServletUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class ApiInterceptor implements HandlerInterceptor {
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LoginServiceImpl loginService;




    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorization = request.getHeader("Authorization");
        try {
            JwtUtils.verify(authorization);
            if (!loginService.is_login(authorization)) {
                ServletUtil.writeJson(response, objectMapper.writeValueAsString(Result.fail().code(403).message("帐号已在其他地方登录")));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
