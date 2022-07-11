package org.example.api.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.api.common.enc.DesUtil;
import org.example.api.model.result.Result;
import org.example.api.web.security.XssHttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;


@WebFilter(filterName = "Filter", urlPatterns = {"/api/*"})
@Slf4j
public class ApiWebFilter implements Filter {
    @Autowired
    ObjectMapper objectMapper;
    @Value("${web.key}")
    private String key;

    public void init(FilterConfig config) throws ServletException {
    }

    //    https://www.baeldung.com/spring-prevent-xss
//    https://blog.csdn.net/weixin_43424932/article/details/104579095
    public void destroy() {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {


        XssHttpServletRequestWrapper req =
                new XssHttpServletRequestWrapper((HttpServletRequest) request);
//        split[]
//        if ( req.getRequestURI().split("/")[1].equals("static")){
//            chain.doFilter(request, response);
//            return;
//        }else if ( req.getRequestURI().split("/")[1].equals("data")){
//            chain.doFilter(request, response);
//            return;
//        }else if (req.getRequestURI().split("/")[1].equals("swagger-ui")){
//            chain.doFilter(request, response);
//            return;
//        }
//        http://127.0.0.1:8080/swagger-ui/index.html

        final HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

        res.addHeader("Access-Control-Allow-Headers", "Content-Type,authorization");
        if (!validation((HttpServletRequest) request)) {
            final String json = objectMapper.writeValueAsString(Result.build(502, "服务器错误"));
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            final Writer writer = response.getWriter();
            writer.write(json);
            response.flushBuffer();
            return;
        }
        chain.doFilter(req, response);
    }


    /***
     * 参数校验
     * @param request
     * @return
     */
    private boolean validation(HttpServletRequest request) {
        String number = DesUtil.getDesUtil(key).decryption(request.getParameter("t"));
        System.out.println("IS_NUMBER" + number);
        if (StringUtils.isNumeric(number)) {
            long timestamp = Long.parseLong(number);
            long unix_timestamp = System.currentTimeMillis() / 1000;
            log.info("clinet timestamp:{} server timestamp:{}", timestamp, unix_timestamp);
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

}
