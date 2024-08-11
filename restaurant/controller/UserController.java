package com.yyh.restaurant.controller;

import com.alibaba.fastjson.JSON;
import com.yyh.restaurant.bean.QueryInfo;
import com.yyh.restaurant.bean.User;
import com.yyh.restaurant.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/alluser")
    public String getUserList(QueryInfo queryInfo) {
        System.out.println(queryInfo);
        int numbers = userDao.getUserCounts("%" + queryInfo.getQuery() + "%");// 获取数据总数
        int pageStart = (queryInfo.getPageNum() - 1) * queryInfo.getPageSize();
        List<User> users = userDao.getAllUser("%" + queryInfo.getQuery() + "%", pageStart, queryInfo.getPageSize());
        HashMap<String, Object> res = new HashMap<>();
        res.put("numbers", numbers);
        res.put("data", users);
        System.out.println("总条数：" + numbers);
        String res_string = JSON.toJSONString(res);
        return res_string;
    }

    @RequestMapping("/userstate")
    public String updateUserState(@RequestParam("id") Integer id,
                                  @RequestParam("state") Boolean state) {

        int i = userDao.updateState(id, state);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/addUser")
    public String addUser(@RequestBody User user) {
        user.setRole("普通用户");
        user.setState(false);
        int i = userDao.addUser(user);
        return i > 0 ? "success" : "error";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(int id){
        int i=userDao.deleteUser(id);
        return i>0? "success":"error";
    }

    @RequestMapping("/getupdate")
    public String getUpdateUser(int id){
        User user=userDao.getUpdateUser(id);
        String string=JSON.toJSONString(user);
        return string;
    }

    @RequestMapping("/edituser")
    public String editUser(@RequestBody User user){
        int i=userDao.editUser(user);
        return i>0?"success":"error";
    }
}
