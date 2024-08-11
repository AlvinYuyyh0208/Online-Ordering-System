package com.yyh.restaurant.dao;

import com.yyh.restaurant.bean.Snack;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SnackDao {
    Snack getSnackByMassage(@Param("snackname") String snackname);
    public List<Snack> getAllSnack(@Param("snackname") String snackname, @Param("pageStart") int PageStart, @Param("pageSize") int pageSize);
    public int getSnackCounts(@Param("snackname") String snackname);
    public int addSnack(Snack snack);
    public int deleteSnack(int id);
    public Snack getUpdateSnack(int id);
    public int editSnack(Snack snack);
}
