package com.yyh.restaurant.dao;

import com.yyh.restaurant.bean.MainMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@Mapper
public interface MenuDao {
    public List<MainMenu> getMenus();
}
