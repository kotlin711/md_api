package org.example.api.web.config;

import org.example.api.web.interceptor.AdminInterceptor;
import org.example.api.web.interceptor.ApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApiInterceptor interceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestUidHandlerMethodArgumentResolver());
    }
    @Value("${web.uploadpath}")
    private String upload_path;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/data/**").addResourceLocations("file:"+new File(upload_path).getAbsolutePath()+"/");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login","/api/user/register","/api/user/getpwd","/api/user/restpwd");



        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**","/ad/**","/file/**","/gift/**","/notice/**","/notice/**","/promo/**"
                ,"/provide/**","/user/**"
                )
                .excludePathPatterns("/admin/login","/admin/auth","/static/**");
        WebMvcConfigurer.super.addInterceptors(registry);

    }
//    @Bean
//    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> errorReportValveCustomizer() {
//
//        return (factory) -> {
//            factory.addContextCustomizers(context -> {
//                final Container parent = context.getParent();
//                if (parent instanceof StandardHost) {
//                    // above class FQCN
//                    ((StandardHost) parent).setErrorReportValveClass(
//                            "com.github.kotlin711.advertisebot.config.CustomErrorReportValve");
//                }
//            });
//        };
//    }
}
