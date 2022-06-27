package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.example.api.model.entity.Order;
import org.example.api.model.req.OrderSearch;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/order")

public class OrderController {
    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/page")
    public PageResult page(OrderSearch orderSearch, @RequestParam(defaultValue = "-1",name = "uid") Integer uid) {
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (uid!=-1){
            orderLambdaQueryWrapper.eq(Order::getUid,uid);
            return PageResult.build(orderService.page(new Page<Order>(orderSearch.getPage(),orderSearch.getLimit()),orderLambdaQueryWrapper).getRecords(), orderService.count());
        }else {
            return PageResult.build(orderService.page(orderSearch), orderService.count());
        }
    }




    @PostMapping("/dels")
    public Result dels(String id){
        if (orderService.removeByIds(Arrays.stream(id.split(",")).collect(Collectors.toList()))) {
            return Result.ok();
        }
        return Result.fail();
    }
    @PostMapping("/del/{id}")
    public Result del(@PathVariable String id){
        if (orderService.removeById(id)) {
            return Result.ok();
        }
        return Result.fail();
    }
}
