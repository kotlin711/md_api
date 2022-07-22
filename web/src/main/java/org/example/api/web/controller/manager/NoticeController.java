package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.model.entity.Notice;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.NoticeServiceImpl;
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
@RequestMapping("/notice")

public class NoticeController {
    @Autowired
    NoticeServiceImpl noticeService;

    @GetMapping("/page")
    public PageResult page(Long page, Long limit) {
        return PageResult.build(noticeService.page(new Page<Notice>(page, limit)).getRecords(), noticeService.count());
    }

    @PostMapping("/dels")
    public Result dels(String id) {
        if (noticeService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id) {
        if (noticeService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/save")
    public Result save(Notice notice) {
        if (noticeService.save(notice)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/update")
    public Result update(Notice notice) {



        if (noticeService.updateById(notice)) {
            return Result.ok();
        }
        return Result.fail();
    }
}
