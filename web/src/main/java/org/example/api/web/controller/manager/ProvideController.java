package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.model.entity.Provide;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.ProvideServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@RequestMapping("/provide")

public class ProvideController {

    @Autowired
    private ProvideServiceImpl service;

    /**
     * 获取文件
     *
     * @param page  页面
     * @param limit 数量
     * @return 文件列表
     */
    @GetMapping("/page")
    public PageResult page(Long page, Long limit) {
        return PageResult.build(service.page(new Page<Provide>(page, limit)).getRecords(), service.count());
    }

    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id) {
        if (service.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("save")
    public Result save(Provide data) {
        if (service.save(data)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/dels")
    public Result dels(String id) {
        if (service.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }
}
