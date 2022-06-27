package org.example.api.web.config;


import org.example.api.common.io.MonitorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class SystemConfig {

    @Value("${web.lib}")
    private String lib;

    @PostConstruct
    public  void  init(){

        MonitorUtil.lib_path ="D:/dev/code/xianyu/6月17/api/web/lib/sigar/";

    }
}
