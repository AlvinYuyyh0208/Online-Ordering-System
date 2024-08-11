package com.yyh.restaurant.dao;

import com.yyh.restaurant.bean.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@Mapper
public interface OrderDao {

    Order getOrderByMassage(@Param("ordername") String ordername);
    public List<Order> getAllOrder(@Param("ordername") String ordername, @Param("pageStart") int PageStart, @Param("pageSize") int pageSize);
    public int getOrderCounts(@Param("ordername") String ordername);
    public int updateState(Integer id,Boolean state);
    public int addOrder(Order order);
    public int deleteOrder(int id);
    public Order getUpdateOrder(int id);
    public int editOrder(Order order);
}
