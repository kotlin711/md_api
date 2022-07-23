package org.example.api.web.controller.api;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.api.common.enc.JwtUtils;
import org.example.api.model.entity.*;
import org.example.api.model.req.api.Restpwd;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.annotation.Userid;
import org.example.api.web.service.impl.*;
import org.example.api.web.util.EmailUtil;
import org.example.api.web.util.FileType;
import org.example.api.web.util.OssUploadUtil;
import org.example.api.web.util.PayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

@Tag(name = "前端APi", description = "前端APi")
@RestController
@Slf4j
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private GiftServiceImpl giftService;
    @Autowired
    private AdServiceImpl adService;
    @Autowired
    private NoticeServiceImpl noticeService;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private StringRedisTemplate template;
    @Value("${web.uploadpath}")
    private String upload_path;
    @Autowired
    private ProvideServiceImpl provideservice;
    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private PromoServiceImpl promoService;
    @Autowired
    private PayUtil payUtil;

    @Autowired
    private LoginServiceImpl loginService;


    @Autowired
    private AppServiceImpl appService;


    public static boolean isValidEmail(String email) {
        if ((email != null) && (!email.isEmpty())) {
            return Pattern.matches("^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$", email);
        }
        return false;
    }

    /**
     * @param page  页面
     * @param limit 数量
     * @param type  类型
     * @return 文件列表
     * @deprecated 获取文件
     */
    @Operation(summary = "获取文件列表", description = "获取文件列表")

    @GetMapping("/file/page")

    public PageResult page(@Parameter(name = "page", description = "页码", in = ParameterIn.QUERY, required = true) @RequestParam(name = "page", defaultValue = "1") Long page, @Parameter(name = "limit", description = "大小", in = ParameterIn.QUERY, required = true) @RequestParam(name = "limit", defaultValue = "10") Long limit, @Parameter(name = "type", description = "类型", in = ParameterIn.QUERY, required = false) @RequestParam(name = "type", defaultValue = "-1") Integer type) {
        LambdaQueryWrapper<MdFile> lambdaQueryWrapper = new LambdaQueryWrapper<MdFile>();
        if (type != -1) {
            lambdaQueryWrapper.eq(MdFile::getType, type);
        }
        return PageResult.build(fileService.page(new Page<MdFile>(page, limit), lambdaQueryWrapper), fileService.count());
    }

    /**
     * @param pwd   密码
     * @param email 邮箱
     * @return 0成功 1失败
     * @deprecated 登录付费
     */
    @Operation(summary = "登录", description = "登录")

    @PostMapping("/user/login")
    public Result login(@Parameter(name = "pwd", description = "密码", in = ParameterIn.QUERY, required = true) String pwd, @Parameter(name = "email", description = "邮箱", in = ParameterIn.QUERY, required = true) String email) {
        if (!userService.getBaseMapper().email_isexist(email)) {
            return Result.fail().message("密码错误或账号不存在");
        }

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail, email);
        userLambdaQueryWrapper.eq(User::getPwd, pwd);
        User one = userService.getOne(userLambdaQueryWrapper);
        if (one != null) {
            if (one.getState() == 0) {
                HashMap<String, String> stringStringHashMap = new HashMap<>();
                stringStringHashMap.put("UID", one.getId().toString());
                stringStringHashMap.put("UNAME", one.getUsername());
                String token = JwtUtils.getToken(stringStringHashMap);
                loginService.save_data(one.getId(), token);
                HashMap<String, Object> data = new HashMap<>();
                one.setPwd(null);
                data.put("token", token);
                data.put("info", one);
                return Result.ok(data);
            }
            return Result.fail().message("账号被冻结");
        }
        return Result.fail().message("密码错误或账号不存在");
    }

    /**
     * @param email 邮箱
     * @return 0 成功 1 请注册
     * @deprecated 重置密码发送邮件
     */
    @Operation(summary = "找回密码", description = "找回密码 向用户发送邮件")
    @PostMapping("/user/getpwd")
    public Result getpwd(@Parameter(name = "email", description = "email", in = ParameterIn.QUERY, required = true) String email) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getEmail, email);
        if (userService.getOne(userLambdaQueryWrapper) != null) {
            emailUtil.send_get_pwd(email);
            return Result.ok().message("邮件以发送");
        }
        return Result.fail().message("账号不存在 请先注册");
    }


    /**
     * @param id id
     * @return
     * @deprecated 获取个人信息
     */
    @Operation(summary = "获取个人信息", description = "个人信息")
    @GetMapping("/user/user_info")
    public User get_user_info(@Userid() @Parameter(hidden = true) String id) {
        return userService.getById(id);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param save_mode
     * @return
     * @throws IOException
     */
    @Operation(summary = "上传头像", description = "上传头像")
    @PostMapping("/user/upload/{save_mode}")
    @ApiResponse(responseCode = "200", description = "文件链接")

    public Result upload(@Parameter(description = "图片文件", in = ParameterIn.QUERY, required = true) @RequestParam("file") MultipartFile file, @Parameter(description = "保存模式", in = ParameterIn.PATH, required = true) @PathVariable int save_mode) throws IOException {
        String fileName = file.getOriginalFilename();
        log.info("上传的文件名为：{}", fileName);
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        int file_type = FileType.getInstance().get_file_type(suffixName);
        long fileSize = file.getSize();
        if (file_type != 2) {
            return Result.fail("只能上传图片");
        }
        MdFile mdFile = new MdFile();
        mdFile.setSaveModel(save_mode);
        mdFile.setName(fileName);
        mdFile.setSize(fileSize);
        mdFile.setType(file_type);
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("file_type", file_type);
        stringStringHashMap.put("fileSize", fileSize);
        String file_path = UUID.randomUUID().toString().replace("-", "") + suffixName;
        if (save_mode == 1) {
            File dir = new File(new File(upload_path).getAbsolutePath() + "/" + file_path.replace(" ", ""));
            String link = "/data/" + file_path.replace(" ", "");
            mdFile.setLink(link);
            file.transferTo(dir);
            stringStringHashMap.put("link", link);
            fileService.save(mdFile);
            return Result.ok(stringStringHashMap);
        } else if (save_mode == 2) {
            if (OssUploadUtil.getInstance().upload(file.getBytes(), fileName)) {

                String path = OssUploadUtil.getInstance().get_path(fileName);
                mdFile.setLink(path);
                stringStringHashMap.put("link", path);

                fileService.save(mdFile);
                return Result.ok(stringStringHashMap);
            }
            return Result.fail();
        }
        return Result.fail();
    }

    @Operation(summary = "更新头像", description = "更新头像")
    @PostMapping("/update_avatar")
    public Result update_avatar(@Parameter(hidden = true) @Userid String id, @Parameter(description = "图片链接 上传文件后返回的链接", in = ParameterIn.QUERY, required = true) String link) {
        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setAvatar(link);
        if (userService.updateById(user)) {
            return Result.ok(user);
        }
        return Result.fail();
    }


    @Operation(summary = "重置密码", description = "重置密码")

    @PostMapping("/user/restpwd")
    public Result restpwd(@RequestBody Restpwd restpwd) {
        String email = restpwd.getEmail();
        if (userService.getBaseMapper().email_isexist(email)) {
            if (template.hasKey(email)) {
                if (template.opsForValue().get(email).toString().equals(restpwd.getCaptcha())) {
                    LambdaQueryWrapper<User> userLambdaQueryWrappe = new LambdaQueryWrapper<>();
                    userLambdaQueryWrappe.eq(User::getEmail, email);
                    User one = userService.getOne(userLambdaQueryWrappe);
                    one.setPwd(restpwd.getPwd());
                    return Result.ok().message("修改成功");
                }
            }
        }
        return Result.fail();
    }

    @Operation(summary = "激活商品", description = "激活商品")
    @PostMapping("/user/activation_vip")
    public Result activation_vip(@Parameter(hidden = true) @Userid String id, @Parameter(description = "激活码", in = ParameterIn.QUERY, required = true) String code) {
        if (giftService.getBaseMapper().does_it_exist(code)) {
            giftService.getBaseMapper().bind(id, code);
            Integer vip = giftService.getBaseMapper().get_name(code);
            User user = userService.getById(id);
            if (user.getVip() != 1) {
                switch (vip) {
                    case 0: {
                        user.setVieEndTime(user.getVieEndTime().plusDays(3));
                    }
                    case 1: {
                        user.setVieEndTime(user.getVieEndTime().plusDays(30));
                    }
                    case 2: {
                        user.setVieEndTime(user.getVieEndTime().plusDays(90 * 4));
                    }
                    case 3: {
                        user.setVieEndTime(user.getVieEndTime().plusDays(90 * 1000));
                    }
                }
            } else {
                user.setVip(vip);

                switch (vip) {
                    case 0: {
                        user.setVieEndTime(LocalDateTime.now().plusDays(3));
                    }
                    case 1: {
                        user.setVieEndTime(LocalDateTime.now().plusDays(30));
                    }
                    case 2: {
                        user.setVieEndTime(LocalDateTime.now().plusDays(90 * 4));
                    }
                    case 3: {
                        user.setVieEndTime(LocalDateTime.now().plusDays(90 * 1000));
                    }
                }
            }

            userService.updateById(user);
            return Result.ok().message("绑定成功");
        }


        return Result.fail().message("不存在");
    }

    @Operation(summary = "获取广告", description = "获取广告")
    @GetMapping("/user/ad")
    public Result ad_list(@Parameter(description = "广告类型", in = ParameterIn.QUERY, required = false) @RequestParam(name = "type", defaultValue = "-1") Integer type, @Parameter(description = "指定标识", in = ParameterIn.QUERY, required = false)

    @RequestParam(name = "flag", defaultValue = "-1") String flag) {
        LambdaQueryWrapper<Ad> adLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (type != -1) {
            adLambdaQueryWrapper.eq(Ad::getType, type);
        }
        if (!flag.equals("-1")) {
            adLambdaQueryWrapper.eq(Ad::getFlag, flag);
        }
        return Result.ok(adService.list(adLambdaQueryWrapper));
    }

    @Operation(summary = "获取公告", description = "获取广告")
    @GetMapping("/user/notice")
    public Result notice_list() {
        return Result.ok(noticeService.getBaseMapper().query_notice());
    }

    @Operation(summary = "商品列表", description = "商品列表", hidden = true)
    @GetMapping("/user/provide")
    public PageResult provide_list(@Parameter(name = "page", description = "页码", in = ParameterIn.QUERY, required = true) @RequestParam(name = "page", defaultValue = "1") Long page, @Parameter(name = "limit", description = "大小", in = ParameterIn.QUERY, required = true) @RequestParam(name = "limit", defaultValue = "10") Long limit) {
        return PageResult.build(provideservice.page(new Page<Provide>(page, limit)).getRecords(), provideservice.count());
    }

    @Operation(summary = "商品列表2", description = "商品列表")
    @GetMapping("/user/promo")
    public PageResult promo_list(@Parameter(name = "page", description = "页码", in = ParameterIn.QUERY, required = true) @RequestParam(name = "page", defaultValue = "1") Long page, @Parameter(name = "limit", description = "大小", in = ParameterIn.QUERY, required = true) @RequestParam(name = "limit", defaultValue = "10") Long limit) {
        return PageResult.build(promoService.page(new Page<Promo>(page, limit)).getRecords(), provideservice.count());
    }


    @Operation(summary = "购买商品", description = "购买商品")
    @PostMapping("/user/provide/pay")
    public Result pay(@Parameter(name = "pid", description = "商品ID", in = ParameterIn.QUERY, required = true) String pid,
                      @Parameter(name = "payType", description = "支付方式", in = ParameterIn.QUERY, required = true) Integer payType,
                      @Parameter(hidden = true) @Userid String uid) throws Exception {

        Order order = new Order();
        order.setUid(Integer.valueOf(uid));

        Promo byId = promoService.getById(pid);




//        byId.get
        order.setPromoTitle(byId.getTitle());
        order.setProvideAmount(byId.getProvideAmount());
        order.setProvideName(byId.getProvideName());
        /*
         * 判断支付类型
         */
        switch (payType) {
            case 1: {
                //zfb
                if (orderService.save(order)) {
                    return payUtil.alipay(byId.getProvideName(), byId.getProvideAmount().toString(), order.getId().toString());
                }
            }
            case 2: {
                //wx
            }
            case 3: {
                //google
            }
        }

        return Result.fail();
    }

    @Operation(summary = "获取商品列表", description = "最新活动商品")
    @GetMapping("/user/provide/now")
    public PageResult now(@Parameter(name = "page", description = "页码", in = ParameterIn.QUERY, required = true) @RequestParam(name = "page", defaultValue = "1") Long page, @Parameter(name = "limit", description = "大小", in = ParameterIn.QUERY, required = true) @RequestParam(name = "limit", defaultValue = "10") Long limit) {

        LambdaQueryWrapper<Promo> promoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        promoLambdaQueryWrapper.ge(Promo::getBeginTime, LocalDateTime.now()).le(Promo::getEndTime, LocalDateTime.now());
        return PageResult.build(promoService.page(new Page<Promo>(page, limit)).getRecords(), promoService.count());
    }

    @Operation(summary = "查询活动价格", description = "查询活动价格")
    @GetMapping("/user/provide/query")
    public Result query_promo(@Parameter(name = "pid", description = "商品id", in = ParameterIn.QUERY, required = true) String pid, @Parameter(name = "vip_type", description = "vip类型", in = ParameterIn.QUERY, required = true) @RequestParam(name = "vip_type", defaultValue = "1") Integer vip_type, @Parameter(hidden = true) @Userid String uid) {
        LambdaQueryWrapper<Promo> promoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        promoLambdaQueryWrapper.eq(Promo::getPid, pid);
        Promo one = promoService.getOne(promoLambdaQueryWrapper);
        if (one != null) {

            return Result.ok(one.getPromoAmount());
        }

        return Result.fail().message("此商品暂无活动");
    }

    @Operation(summary = "用户注册", description = "用户注册 注册成功返回TOKEN")
    @PostMapping("/user/register")
    public Result register(@RequestBody User user) {
        if (isValidEmail(user.getEmail())) {
            if (userService.getBaseMapper().email_isexist(user.getEmail())) {
                return Result.fail().message("账号以存在");
            }
            user.setVip(0);
            if (user.getUsername().length() >= 2 || user.getUsername().length() <= 8) {
                if (user.getPwd().length() >= 6 || user.getPwd().length() < 8) {
                    if (userService.save(user)) {
                        HashMap<String, String> stringStringHashMap = new HashMap<>();
                        stringStringHashMap.put("UID", user.getId().toString());
                        stringStringHashMap.put("UNAME", user.getUsername());
                        String token = JwtUtils.getToken(stringStringHashMap);
                        loginService.save_data(user.getId(), token);
                        HashMap<String, Object> data = new HashMap<>();
                        user.setPwd(null);
                        data.put("token", token);
                        data.put("info", user);
                        return Result.ok(data);
                    } else {
                        return Result.fail();
                    }

                }
            }
        }
        return Result.fail();
    }


    @Operation(summary = "校验app", description = "校验app版本号 app存在返回成功 ")
    @PostMapping("/check_app")
    public Result check_app(String version, String md5, String size) {
        LambdaQueryWrapper<MdApp> mdAppLambdaQueryWrapper = new LambdaQueryWrapper<>();
        mdAppLambdaQueryWrapper.eq(MdApp::getVersion, version);
        mdAppLambdaQueryWrapper.eq(MdApp::getMd5, md5);
        mdAppLambdaQueryWrapper.eq(MdApp::getSize, size);
        if (appService.getOne(mdAppLambdaQueryWrapper) != null) {
            return Result.ok();
        }
        return Result.fail();
    }


}
