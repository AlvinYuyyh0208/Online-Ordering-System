package com.yyh.restaurant.controller;

import com.alibaba.fastjson.JSON;
import com.yyh.restaurant.bean.User;
import com.yyh.restaurant.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;

@RestController
@CrossOrigin("*")
public class LoginController {

    @Resource
    UserDao userDao;

    @RequestMapping("/login")
    public String login(@RequestBody User user){
        String flag="error";
        User us=userDao.getUserByMassage(user.getUsername(),user.getPassword());
        System.out.println("user:"+us);
        HashMap<String,Object> res=new HashMap<>();
        if(us!=null){
            flag="ok";
        }

        res.put("flag",flag);
        res.put("user",user);

        String res_json= JSON.toJSONString(res);
        return res_json;

    }
}
