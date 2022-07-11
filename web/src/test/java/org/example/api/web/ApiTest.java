package org.example.api.web;

import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import org.example.api.common.enc.DesUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

public class ApiTest {
    @Value("${web.key}")
    private String key;
    @Test
    public    void  test_get_pwd_and_test_timestamp(){
        HTTP http = HTTP.builder().build();
        HttpResult authorization = http.sync("http://localhost:8080/api/user/getpwd?email=1503942847@qq.com")
                .addBodyPara("t",DesUtil.getDesUtil(key).encryption(String.valueOf((System.currentTimeMillis()/1000))))
                .post();
        System.out.println(authorization.getTask().getUrl());
        System.out.println(authorization.getBody().toString());
    }
    @Test

    public    void  test_xss(){
        HTTP http = HTTP.builder().build();

//
        String baidu = http.sync("http://localhost:8080/api/user/getpwd?email=<a/>")
                .addBodyPara("t", DesUtil.getDesUtil(key).encryption(String.valueOf((System.currentTimeMillis()/1000))))
                .post()
                .getBody()
                .toString();
        System.out.println(baidu);
    }
    @Test

    public    void  test_jwt(){
        HTTP http = HTTP.builder().build();
        String s = String.valueOf((System.currentTimeMillis() / 1000));
//
        HttpResult authorization = http.sync("http://localhost:8080/api/user/user_info?t=" + DesUtil.getDesUtil(key).encryption(s))
                .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJVSUQiOiIxMCIsImV4cCI6MTY1NTg3NTE5Mn0.YIq9TSRJj8F45qaLuWRsElhE1WnsVyj3Bf6PXBgYP14")
                .get();
        System.out.println(authorization.getTask().getUrl());
        System.out.println(authorization.getBody().toString());
    }
}
