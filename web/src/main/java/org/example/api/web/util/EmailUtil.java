package org.example.api.web.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public final class EmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String form;

    @Autowired
    private StringRedisTemplate template;

    public void send_get_pwd(String email) {
        //        template.hasKey(ip)

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setFrom(form);
        msg.setSubject("密码找回");
        String code = RandomStringUtils.randomNumeric(6);
        msg.setText("你的验证码是:" + code + "\n五分钟有效");
        template.opsForValue().set(email, code, 60 * 5, TimeUnit.SECONDS);

        javaMailSender.send(msg);
        log.info("发送成功");


    }

}
