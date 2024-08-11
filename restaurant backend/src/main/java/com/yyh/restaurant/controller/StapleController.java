package com.yyh.restaurant.controller;

import com.alibaba.fastjson.JSON;
import com.yyh.restaurant.bean.Staple;
import com.yyh.restaurant.bean.QueryInfo;
import com.yyh.restaurant.dao.StapleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class StapleController {


    @Autowired
    private StapleDao stapleDao;

    @RequestMapping("/allstaple")
    public String getStapleList(QueryInfo queryInfo) {
        System.out.println(queryInfo);
        int numbers = stapleDao.getStapleCounts("%" + queryInfo.getQuery() + "%");// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        List<Staple> Staples = stapleDao.getAllStaple("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", Staples);
        System.out.println("总条数：" + numbers);
        String res_string = JSON.toJSONString(res);
        return res_string;
    }



    @RequestMapping("/addStaple")
    public String addStaple(@RequestBody Staple staple) {


        int i = stapleDao.addStaple(staple);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/deleteStaple")
    public String deleteStaple(int id){
        int i=stapleDao.deleteStaple(id);
        return i>0? "success":"error";
    }

    @RequestMapping("/updateStaple")
    public String getUpdateStaple(int id){
        Staple staple=stapleDao.getUpdateStaple(id);
        String string=JSON.toJSONString(staple);
        return string;
    }

    @RequestMapping("/editStaple")
    public String editStaple(@RequestBody Staple Staple){
        int i=stapleDao.editStaple(Staple);
        return i>0?"success":"error";
    }
}
