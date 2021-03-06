package org.example.api.web.controller.manager;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/admin")
@Controller

public class ManageController {


    @GetMapping("/index")
    public  String index(){
        return "index/index";
    }
    @GetMapping("/welcome")
    public  String welcome(){
        return "index/welcome";
    }
    @GetMapping("/list")
    public String admin_list(){
        return "admin/list";
    }
    @GetMapping("/add")
    public String admin_add(){
        return "admin/add";
    }

    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("USERNAME");
        return "admin/login";
    }
    @GetMapping("/file/list")
    public  String file_list(){
        return "files/list";
    }


    @GetMapping("/user/list")
    public  String user_list(){
        return "user/list";
    }
    @GetMapping("/user/order_list/{id}")
    public  String user_order_list(@PathVariable String id, Model model){
        model.addAttribute("id",id);
        return "user/orderlist";
    }
    @GetMapping("/user/add")
    public  String user_add(){
        return "user/add";
    }


    @GetMapping("/order/list")
    public  String order_list(){
        return "order/list";
    }

//    gift
    @GetMapping("/notice/add")
    public String notice_add(){
        return "notice/add";
    }
    @GetMapping("/notice/list")
    public String notice_list(){
        return "notice/list";
    }


    @GetMapping("/gift/add")
    public String gift_add(){
        return "gift/add";
    }
    @GetMapping("/gift/list")
    public String gift_list(){
        return "gift/list";
    }


    @GetMapping("/ad/list")
    public String ad_list(){
        return "ad/list";
    }
    @GetMapping("/ad/add")
    public String ad_add(){
        return "ad/add";
    }



    @GetMapping("/provide/list")
    public String provide_list(){
        return "provide/list";
    }
    @GetMapping("/provide/add")
    public String provide_add(){
        return "provide/add";
    }
    @GetMapping("/promo/list")
    public String promo_list(){
        return "provide/promo_list";
    }
    @GetMapping("/promo/add/{id}/{amount}/{name}")
    public String promo_add(@PathVariable String id, Model model, @PathVariable String amount, @PathVariable String name){

        model.addAttribute("pid",id);
        model.addAttribute("amount",amount);
        model.addAttribute("name",name);
        return "provide/promo_add";
    }



}
