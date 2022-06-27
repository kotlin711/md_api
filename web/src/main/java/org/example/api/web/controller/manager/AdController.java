package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.model.entity.Ad;
import org.example.api.model.entity.MdFile;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.AdServiceImpl;
import org.example.api.web.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author crying711
 * @since 2022-06-17
 */

@RestController
@RequestMapping("/ad")
public class AdController {




    @Autowired
    AdServiceImpl adService;




    /**
     * 获取文件
     * @param page 页面
     * @param limit 数量
     * @return  文件列表
     */
    @GetMapping("/page")
    public PageResult page(Long page, Long limit) {
        return PageResult.build(adService.page(new Page<Ad>(page,limit)).getRecords(), adService.count());
    }
    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id){
        if (adService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }

    @PostMapping("/dels")
    public Result dels(String id){
        if (adService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(Ad file,String start,String end){
        file.setRrule(start + ">" + end);
        if (adService.save(file)) {
            return Result.ok();
        }
        return  Result.fail();
    }
    /**
     * 保存
     */
    @PostMapping("/update")
    public Result update(Ad file){
        if (adService.updateById(file)) {
            return Result.ok();
        }
        return  Result.fail();
    }

}
