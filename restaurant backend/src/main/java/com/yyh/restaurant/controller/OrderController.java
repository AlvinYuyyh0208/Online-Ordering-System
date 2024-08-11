package com.yyh.restaurant.controller;

import com.alibaba.fastjson.JSON;
import com.yyh.restaurant.bean.QueryInfo;
import com.yyh.restaurant.bean.Order;
import com.yyh.restaurant.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderDao orderDao;

    @RequestMapping("/allorder")
    public String getOrderList(QueryInfo queryInfo) {
        System.out.println(queryInfo);
        int numbers = orderDao.getOrderCounts("%" + queryInfo.getQuery() + "%");// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        List<Order> orders = orderDao.getAllOrder("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", orders);
        System.out.println("总条数：" + numbers);
        return JSON.toJSONString(res);
    }

    @RequestMapping("/orderstate")
    public String updateOrderState(@RequestParam("id") Integer id,
                                  @RequestParam("state") Boolean state) {
        //System.out.println(id+" "+state);
        int i = orderDao.updateState(id, state);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/addOrder")
    public String addOrder(@RequestBody Order order) {
        order.setState(0);
        int i = orderDao.addOrder(order);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/deleteOrder")
    public String deleteOrder(int id){
        int i=orderDao.deleteOrder(id);
        return i>0? "success":"error";
    }

    @RequestMapping("/updateOrder")
    public String getUpdateOrder(int id){
        Order order=orderDao.getUpdateOrder(id);
        String string=JSON.toJSONString(order);
        return string;
    }

    @RequestMapping("/editorder")
    public String editOrder(@RequestBody Order order){
        int i=orderDao.editOrder(order);
        return i>0?"success":"error";
    }
}
