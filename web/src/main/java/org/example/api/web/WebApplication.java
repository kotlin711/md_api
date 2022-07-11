package org.example.api.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


//http://13qquig9cn.htrei.xyz/admin/login
@MapperScan("org.example.api.web.mapper")
@SpringBootApplication
@ServletComponentScan("org.example.api.web.servlet")

public class WebApplication {
    //    用springboot开发APP后台管理及接口，详情见思维导图：
//    数据库 http://mindline.cn/file/2RU1ZR
//
//  后台  http://mindline.cn/file/24UC8C
//
//   前台 http://mindline.cn/file/5C6PP0
    public static void main(String[] args) {

        SpringApplication.run(WebApplication.class, args);
    }

}
