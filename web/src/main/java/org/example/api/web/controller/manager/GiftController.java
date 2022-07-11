package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.api.model.entity.Gift;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.GiftServiceImpl;
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
@RequestMapping("/gift")

public class GiftController {
    @Autowired
    GiftServiceImpl giftService;


    /**
     * 获取文件
     *
     * @param page  页面
     * @param limit 数量
     * @return 文件列表
     */
    @GetMapping("/page")
    public PageResult page(Long page, Long limit) {
        return PageResult.build(giftService.page(new Page<Gift>(page, limit)).getRecords(), giftService.count());
    }

    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id) {
        if (giftService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/dels")
    public Result dels(String id) {
        if (giftService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(Integer quantity, Integer length, Integer rule, Integer vip) {
        for (int i = 0; i < quantity; i++) {
            Gift gift = new Gift();
            gift.setProvideName(vip);
            switch (rule) {
                case 1: {
                    gift.setKey(RandomStringUtils.randomAlphanumeric(length));
                    break;
                }
                case 2: {
                    gift.setKey(RandomStringUtils.randomAlphabetic(length));
                    break;
                }
                case 3: {
                    gift.setKey(RandomStringUtils.randomNumeric(length));
                    break;
                }
            }
            giftService.save(gift);
        }
        return Result.ok();
    }

}
