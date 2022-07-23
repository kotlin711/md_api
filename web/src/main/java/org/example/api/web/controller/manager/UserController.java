package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.common.io.MonitorUtil;
import org.example.api.model.dto.UserCountDto;
import org.example.api.model.entity.User;
import org.example.api.model.result.LineChat;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */
@RestController
@RequestMapping("/user")

//用户信息面板:
//        用户名(username)，
//        密码(pwd)，
//        头像(avatar)，
//        邮箱(email)
//        vip(vip_type(int))（普通会员、体验会员(体验时间为3天)、季度会员、年度会员、永久会员)，
//        订单(点击链接该用户订单页)，
//        上次登录时间，
//        vip到期时间，
//        注册时间，
//        用户状态(正常、冻结)，
//        登录ip，
//        设备id


public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/page")
    public PageResult page(Long page, Long limit,
                           @RequestParam(defaultValue = "-1", name = "vip") Integer vip,
                           @RequestParam(defaultValue = "", name = "username") String username,
                           @RequestParam(defaultValue = "", name = "start") String start,
                           @RequestParam(defaultValue = "", name = "end") String end,
                           @RequestParam(defaultValue = "-1", name = "sort") Integer sort) {

        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!username.equals("")) {
            userLambdaQueryWrapper.like(User::getUsername, username);
        }
        if (!start.equals("") && !end.equals("")) {
            userLambdaQueryWrapper.ge(User::getRegTime, start);
            userLambdaQueryWrapper.le(User::getRegTime, end);
        }
        if (sort != -1) {

            switch (sort) {

                case 0: {

                    userLambdaQueryWrapper.orderByAsc(User::getRegTime);

                    break;
                }
                case 1: {
                    userLambdaQueryWrapper.orderByDesc(User::getRegTime);

                    break;
                }
                case 2: {
                    userLambdaQueryWrapper.orderByAsc(User::getLastLoginTime);

                    break;
                }
                case 3: {
                    userLambdaQueryWrapper.orderByDesc(User::getLastLoginTime);
                    break;
                }
            }
        }


        if (vip != -1) {
            userLambdaQueryWrapper.eq(User::getVip, vip);
        }
        return PageResult.build(userService.page(new Page<User>(page, limit), userLambdaQueryWrapper).getRecords(), userService.count(userLambdaQueryWrapper));
    }


    @PostMapping("/update_state/{id}")
    public Result update_state(@PathVariable String id) {
        userService.getBaseMapper().update_state(id);
        return Result.ok();
    }

    @PostMapping("/save")
    public Result save(User user) {

        switch (user.getVip()) {
            case 1: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90));
                break;
            }
            case 2: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90 * 2));
                break;
            }
            case 3: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90 * 4));
                break;
            }
            case 4: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90 * 9999));
                break;
            }
        }

        if (userService.save(user)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/update")
    public Result update(User user) {


        System.out.println("VIP:"+user.getVip());
        switch (user.getVip()) {
            case 1: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90));
                break;
            }
            case 2: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90 * 2));
                break;
            }
            case 3: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90 * 4));
                break;
            }
            case 4: {
                user.setVieEndTime(LocalDateTime.now().plusDays(90 * 9999));
                break;
            }


        }
        if (userService.updateById(user)) {
            return Result.ok();
        }
        return Result.fail();
    }
    @PostMapping("/dels")
    public Result dels(String id) {
        if (userService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id) {
        if (userService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }


    @GetMapping("/system_info")
    public Result info() {
        final MonitorUtil.CpuVO cpuVO = MonitorUtil.getInstance().cpu_info();
        final MonitorUtil.MemoryVO memoryVO = MonitorUtil.getInstance().memory_info();
        final MonitorUtil.ProcessInfoVo java = MonitorUtil.getInstance().process_Info(String.valueOf(MonitorUtil.getInstance().getSigar().getPid()), "java");
       final MonitorUtil.DiskVO diskVO = MonitorUtil.getInstance().disk_info("/");
       final MonitorUtil.JVMInfoVO jvmInfoVO = MonitorUtil.getInstance().jvm_info();


        Map<String, Object> map = new HashMap<>();
        map.put("cpu", cpuVO);
        map.put("jvm", jvmInfoVO);

        map.put("disk", diskVO);
        map.put("mem", memoryVO);
        map.put("java", java);
        map.put("user", userService.getBaseMapper().user_count());
        List<UserCountDto> userCountDtos = userService.getBaseMapper().user_count_sevenday();
        List<UserCountDto> vipUserCountSevenday = userService.getBaseMapper().vip_user_count_sevenday();

        map.put("user_line", new LineChat(userCountDtos.stream().map(UserCountDto::getDate).collect(Collectors.toList()),
                userCountDtos.stream().map(UserCountDto::getValue).collect(Collectors.toList())));
        map.put("vip", new LineChat(vipUserCountSevenday.stream().map(UserCountDto::getDate).collect(Collectors.toList()),
                vipUserCountSevenday.stream().map(UserCountDto::getValue).collect(Collectors.toList())));
        return Result.ok(map);
    }

    @GetMapping("/network")
    public Result net_work() {
        return Result.ok(MonitorUtil.getInstance().getMetricVo());
    }


}
