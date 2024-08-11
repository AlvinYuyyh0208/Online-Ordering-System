package com.yyh.restaurant.dao;

import com.yyh.restaurant.bean.Staple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface StapleDao {

    Staple getStapleByMassage(@Param("staplename") String staplename);
    public List<Staple> getAllStaple(@Param("staplename") String staplename, @Param("pageStart") int PageStart, @Param("pageSize") int pageSize);
    public int getStapleCounts(@Param("staplename") String staplename);
    public int addStaple(Staple staple);
    public int deleteStaple(int id);
    public Staple getUpdateStaple(int id);
    public int editStaple(Staple staple);
}
