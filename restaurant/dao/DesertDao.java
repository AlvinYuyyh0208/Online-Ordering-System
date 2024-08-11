package com.yyh.restaurant.dao;

import com.yyh.restaurant.bean.Desert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Mapper
public interface DesertDao {
    Desert getDesertByMassage(@Param("desertname") String desertname);
    public List<Desert> getAllDesert(@Param("desertname") String desertname, @Param("pageStart") int PageStart, @Param("pageSize") int pageSize);
    public int getDesertCounts(@Param("desertname") String desertname);
    public int addDesert(Desert desert);
    public int deleteDesert(int id);
    public Desert getUpdateDesert(int id);
    public int editDesert(Desert desert);
}
