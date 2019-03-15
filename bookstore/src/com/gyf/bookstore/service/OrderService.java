package com.gyf.bookstore.service;

import com.gyf.bookstore.dao.OrderDao;
import com.gyf.bookstore.dao.OrderItemDao;
import com.gyf.bookstore.dao.ProductDao;
import com.gyf.bookstore.model.Order;
import com.gyf.bookstore.model.OrderItem;
import com.gyf.bookstore.utils.ManagerThreadLocal;

import java.sql.SQLException;
import java.util.List;

public class OrderService {

    /**
     * 添加订单的业务方法
     *
     * 如果在service中执行多条sql语句，不要忘记添加事务
     *
     * @param order
     */

    private OrderDao orderDao = new OrderDao();
    private OrderItemDao orderItemDao = new OrderItemDao();
    private ProductDao productDao = new ProductDao();
    public void creatOrder(Order order){

        try {
            //开启事务
            ManagerThreadLocal.beginTransaction();
            //1.插入订单表
            orderDao.add(order);
            //2.插入订单详情表
            orderItemDao.addItems(order.getItems());
            //3.减库存
            for(OrderItem item : order.getItems()){
                productDao.updatePNum(item.getProduct().getId(), item.getBuynum());
            }

            //结束事务
            ManagerThreadLocal.commitTransaction();

        } catch (SQLException e) {
            e.printStackTrace();

            //事务回滚
            ManagerThreadLocal.rollbackTransaction();
        }

    }

    public List<Order> findOrdersByUserId(String userid){

        try {
            return orderDao.findOrdersByUserId(userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }


    public Order findOrdersByOrderId(String orderId){

        try {
            return orderDao.findOrderByOrderId(orderId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }
}
