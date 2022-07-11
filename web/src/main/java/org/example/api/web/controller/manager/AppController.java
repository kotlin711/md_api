package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.model.entity.MdApp;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.AppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequestMapping("/app")
@RestController
public class AppController {
    @Autowired
    AppServiceImpl appService;


    /**
     * 获取文件
     *
     * @param page  页面
     * @param limit 数量
     * @return 文件列表
     */
    @GetMapping("/page")
    public PageResult page(Long page, Long limit) {
        return PageResult.build(appService.page(new Page<MdApp>(page, limit)).getRecords(), appService.count());
    }

    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id) {
        if (appService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/dels")
    public Result dels(String id) {
        if (appService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(MdApp app) {

        if (appService.save(app)) {
            return Result.ok();
        }
        return Result.fail();
    }
}
