package org.example.api.model.req.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "重置密码")
public class Restpwd {

    @Schema(name = "captcha",description = "验证码")

    private String captcha;

    @Schema(name = "email",description = "邮件地址")
    private String email;

    @Schema(name = "pwd",description = "密码")
    private String pwd;
}
