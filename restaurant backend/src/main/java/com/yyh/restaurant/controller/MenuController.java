package com.yyh.restaurant.controller;


import com.alibaba.fastjson.JSON;
import com.yyh.restaurant.bean.MainMenu;
import com.yyh.restaurant.dao.MenuDao;
import org.apache.catalina.LifecycleState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class MenuController {

    @Resource
    MenuDao menuDao;
    @RequestMapping("/menus")
    public String getAllMenus(){
        System.out.println("访问成功");
        HashMap<String,Object> data= new HashMap<>();
        int status =404;
        List<MainMenu> menus = menuDao.getMenus();
        if(menus!=null){
            data.put("menus",menus);
            data.put("flag",200);
        }else{
            data.put("flag",404);
        }
        String s= JSON.toJSONString(data);

        return s;

    }
}
