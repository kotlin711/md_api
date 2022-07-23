package org.example.api.web.controller.api;

import lombok.AllArgsConstructor;
import org.example.api.model.entity.Order;
import org.example.api.web.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * https://blog.csdn.net/weixin_45111030/article/details/118306138
 * https://www.nhooo.com/note/qaguct.html
 */
@RestController
@RequestMapping(value = "/pay")
@AllArgsConstructor

public class PayController {


    @Autowired
    private OrderServiceImpl service;


    /**
     * 支付成功后的支付宝回调
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/fallback")
    public Object notifyAsync(HttpServletRequest request) {
        Map map = request.getParameterMap();
        System.out.println("--------------->支付完成时，服务端返回的参数" + map.toString());
        System.out.println(map.toString());
        String body = (String) map.get("body");
        String out_trade_no = (String) map.get("out_trade_no");//获取商户之前传给支付宝的订单号（商户系统的唯一订单号）
        String trade_no = (String) map.get("trade_no");//支付宝的交易号
        String total_amount = (String) map.get("total_amount");
        String time_stamp = (String) map.get("time_stamp");
        String trade_status = (String) map.get("trade_status");
        System.out.println("---------->" + body);
        System.out.println("------------>" + time_stamp);
        Order byId = service.getById(out_trade_no);
        byId.setOrderId(trade_no);
        if (trade_status.equals("TRADE_SUCCESS")) {
            byId.setResults(1);
        }
        service.updateById(byId);
        return "Ok";
    }


}

