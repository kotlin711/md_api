package org.example.api.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.api.common.enc.DesUtil;
import org.example.api.model.result.Result;
import org.example.api.web.security.XssHttpServletRequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;



@WebFilter(filterName = "Filter",urlPatterns = {"/*"})
@Slf4j
public class ApiWebFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    //    https://www.baeldung.com/spring-prevent-xss
//    https://blog.csdn.net/weixin_43424932/article/details/104579095
    public void destroy() {
    }

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {



        XssHttpServletRequestWrapper req =
                new XssHttpServletRequestWrapper((HttpServletRequest) request);
//        split[]
        if ( req.getRequestURI().split("/")[1].equals("static")){
            chain.doFilter(request, response);
            return;
        }else if ( req.getRequestURI().split("/")[1].equals("data")){
            chain.doFilter(request, response);
            return;
        }else if (req.getRequestURI().split("/")[1].equals("swagger-ui")){
            chain.doFilter(request, response);
            return;
        }
//        http://127.0.0.1:8080/swagger-ui/index.html

//        System.out.println();
        final HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Credentials", "true");
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");

        res.addHeader("Access-Control-Allow-Headers", "Content-Type,authorization");

        chain.doFilter(req, response);
    }




}
