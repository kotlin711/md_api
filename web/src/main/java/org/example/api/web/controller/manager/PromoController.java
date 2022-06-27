package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.extern.slf4j.Slf4j;
import org.example.api.model.entity.Promo;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.PromoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@RequestMapping("/promo")
@Slf4j

public class PromoController {

    @Autowired
    private PromoServiceImpl service;

    /**
     * 获取文件
     *
     * @param page  页面
     * @param limit 数量
     * @return 文件列表
     */
    @GetMapping("/page")
    public PageResult page(Long page, Long limit) {
        return PageResult.build(service.page(new Page<Promo>(page, limit)).getRecords(), service.count());
    }

    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id) {
        if (service.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("save")
    public Result save(Promo data, String  begin, String  end) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime beginTime = LocalDateTime.parse(begin, formatter);
        LocalDateTime endTime = LocalDateTime.parse(end, formatter);
        data.setBeginTime(beginTime);
       data.setEndTime(endTime);

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

    @GetMapping("/does_it_exist/{id}")
    public Result does_it_exist(@PathVariable String id) {
        boolean b = service.getBaseMapper().does_it_exist(id);
        log.info("does_it_exist {}",b);
        if (!b) {
            return Result.ok();
        }
        return Result.fail();
    }
}
