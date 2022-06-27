package org.example.api.web.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.api.common.enc.DesUtil;
import org.example.api.common.enc.JwtUtils;
import org.example.api.model.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

@Slf4j
@Component
public class ApiInterceptor implements HandlerInterceptor {
    @Autowired
    ObjectMapper objectMapper;
    @Value("${web.key}")
    private String key;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String authorization = request.getHeader("Authorization");


        if (!validation(request)) {
            final String json = objectMapper.writeValueAsString(Result.build(502, "服务器错误"));
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            final Writer writer = response.getWriter();
            writer.write(json);
            response.flushBuffer();
            return false;
        }
        JwtUtils.verify(authorization);
        return  true;
    }
    /***
     * 参数校验
     * @param request
     * @return
     */
    private boolean validation(HttpServletRequest request) {
        String number = DesUtil.getDesUtil(key).decryption(request.getParameter("t"));
        if (StringUtils.isNumeric(number)) {
            long timestamp = Long.parseLong(number);
            long unix_timestamp = System.currentTimeMillis()/1000;
            log.info("clinet timestamp:{} server timestamp:{}",timestamp,unix_timestamp);
            /*
                客户端请求服务器时间戳不能大约30秒
             * 服务器和客户端的时间戳不能大于30秒
             */
            if (unix_timestamp - timestamp >= 40) {
                log.info("validation时间太大");
                return false;
            }
            log.info("validation 校验成功");
            return true;
        }
        log.info("validation不是数字");
        return false;

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
