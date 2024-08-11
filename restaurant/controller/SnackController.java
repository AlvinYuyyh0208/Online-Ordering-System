package com.yyh.restaurant.controller;

import com.alibaba.fastjson.JSON;
import com.yyh.restaurant.bean.Snack;
import com.yyh.restaurant.bean.QueryInfo;
import com.yyh.restaurant.dao.SnackDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class SnackController {


    @Autowired
    private SnackDao snackDao;

    @RequestMapping("/allsnack")
    public String getSnackList(QueryInfo queryInfo) {
        System.out.println(queryInfo);
        int numbers = snackDao.getSnackCounts("%" + queryInfo.getQuery() + "%");// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        List<Snack> Snacks = snackDao.getAllSnack("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", Snacks);
        System.out.println("总条数：" + numbers);
        String res_string = JSON.toJSONString(res);
        return res_string;
    }



    @RequestMapping("/addSnack")
    public String addSnack(@RequestBody Snack snack) {


        int i = snackDao.addSnack(snack);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/deleteSnack")
    public String deleteSnack(int id){
        int i=snackDao.deleteSnack(id);
        return i>0? "success":"error";
    }

    @RequestMapping("/updateSnack")
    public String getUpdateSnack(int id){
        Snack snack=snackDao.getUpdateSnack(id);
        String string=JSON.toJSONString(snack);
        return string;
    }

    @RequestMapping("/editSnack")
    public String editSnack(@RequestBody Snack Snack){
        int i=snackDao.editSnack(Snack);
        return i>0?"success":"error";
    }
}
