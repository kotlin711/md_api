package org.example.api.web.controller.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.api.model.entity.Admin;
import org.example.api.model.entity.Gift;
import org.example.api.model.result.PageResult;
import org.example.api.model.result.Result;
import org.example.api.web.service.impl.AdminServiceImpl;
import org.example.api.web.service.impl.GiftServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author crying711
 * @since 2022-06-27
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;




    /**
     * 获取文件
     * @param page 页面
     * @param limit 数量
     * @return  文件列表
     */
    @GetMapping("/page")
    public PageResult page(Long page, Long limit) {
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.ne(Admin::getId,"admin");
        return PageResult.build(adminService.page(new Page<Admin>(page,limit)).getRecords(), adminService.count());
    }


    @GetMapping("/del/{id}")
    public Result del(@PathVariable String id) {

        if (adminService.removeById(id)) {
            return Result.ok();
        }
        return  Result.fail();
    }

    @PostMapping("/auth")
    public Result auth(String name, String pwd, HttpServletRequest request) {
        if (adminService.getBaseMapper().login(name,pwd)) {
            request.getSession().setAttribute("USERNAME",name);
         return Result.ok();
        }
        return  Result.fail();
    }

}
