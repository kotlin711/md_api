package org.example.api.web.config;


import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.example.api.common.io.MonitorUtil;
import org.example.api.web.service.impl.NoticeServiceImpl;
import org.example.api.web.service.impl.ProvideServiceImpl;
import org.example.api.web.util.OssUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.management.Query;
import java.io.File;

@Configuration
public class SystemConfig {

    @Value("${web.lib}")
    private String lib;
    @Value("${web.uploadpath}")
    private String upload_path;

    @Value("${web.oss.sk}")
    private String sk;
    @Value("${web.oss.ak}")
    private String ak;
    @Value("${web.oss.bk}")
    private String bk;
    @Value("${web.oss.domain}")
    private String domain;

    @Value("${alipay.appId}")
    private String appId;

    @Value("${alipay.privateKey}")
    private String privateKey;

    @Value("${alipay.publicKey}")
    private String publicKey;

    @Value("${alipay.gateway}")
    private String gateway;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;







    @PostConstruct
    public void init() {
        MonitorUtil.lib_path = lib;
        System.out.println("**********JNI-初始化完成**********");
        OssUploadUtil.getInstance(ak, sk, bk, domain);
        System.out.println("**********OSS-SDK初始化完成**********");
        Factory.setOptions(getOptions());
        System.out.println("**********支付宝SDK初始化完成**********");
        if (!new File(upload_path).exists()){
            System.out.println("**********上传文件目录初始化失败**********");
            System.exit(0);
        }

    }


    private Config getOptions() {
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = this.gateway;
        config.signType = "RSA2";

        config.appId = this.appId;

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = this.privateKey;


        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        config.alipayPublicKey = this.publicKey;

        //可设置异步通知接收服务地址（可选）

        config.notifyUrl = this.notifyUrl;

        return config;
    }
}
