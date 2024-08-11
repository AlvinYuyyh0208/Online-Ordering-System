package com.yyh.restaurant.controller;


import com.alibaba.fastjson.JSON;
import com.yyh.restaurant.bean.Desert;
import com.yyh.restaurant.bean.QueryInfo;
import com.yyh.restaurant.dao.DesertDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class DesertController {

    @Autowired
    private DesertDao desertDao;

    @RequestMapping("/alldesert")
    public String getDesertList(QueryInfo queryInfo) {
        System.out.println(queryInfo);
        int numbers = desertDao.getDesertCounts("%" + queryInfo.getQuery() + "%");// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        List<Desert> Deserts = desertDao.getAllDesert("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", Deserts);
        System.out.println("总条数：" + numbers);
        String res_string = JSON.toJSONString(res);
        return res_string;
    }



    @RequestMapping("/addDesert")
    public String addDesert(@RequestBody Desert desert) {


        int i = desertDao.addDesert(desert);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/deleteDesert")
    public String deleteDesert(int id){
        int i=desertDao.deleteDesert(id);
        return i>0? "success":"error";
    }

    @RequestMapping("/updateDesert")
    public String getUpdateDesert(int id){
        Desert desert=desertDao.getUpdateDesert(id);
        String string=JSON.toJSONString(desert);
        return string;
    }

    @RequestMapping("/editDesert")
    public String editDesert(@RequestBody Desert Desert){
        int i=desertDao.editDesert(Desert);
        return i>0?"success":"error";
    }
}

