package com.yyh.restaurant.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

//全局配置类配置跨域请求
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("Http://localhost:8080","null")
                .allowedMethods("GET","POST","PUT","OPTIONS","DELETE")
                .maxAge(3600);
    }

}
