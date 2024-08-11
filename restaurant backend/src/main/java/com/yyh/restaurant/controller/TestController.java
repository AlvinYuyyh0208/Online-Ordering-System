package com.yyh.restaurant.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.sax.SAXResult;

@RestController
@CrossOrigin("*")
public class TestController {

    @RequestMapping("/test")
    public String test(){
        return "ok";
    }
}
