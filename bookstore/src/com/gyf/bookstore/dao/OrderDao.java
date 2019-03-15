package com.gyf.bookstore.dao;

import com.gyf.bookstore.model.Order;
import com.gyf.bookstore.model.OrderItem;
import com.gyf.bookstore.model.Product;
import com.gyf.bookstore.utils.C3P0Utils;
import com.gyf.bookstore.utils.ManagerThreadLocal;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    /**
     * 添加一张订单
     * @param order
     */
    public void add(Order order) throws SQLException {
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";

        List<Object> params = new ArrayList<Object>();
        params.add(order.getId());
        params.add(order.getMoney());
        params.add(order.getReceiverAddress());
        params.add(order.getReceiverName());
        params.add(order.getReceiverPhone());
        params.add(order.getPaystate());
        params.add(order.getOrdertime());
        params.add(order.getUser().getId());

        //3.执行
       /* QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        qr.update(sql, params.toArray());
*/
        QueryRunner qr = new QueryRunner();
        qr.update(ManagerThreadLocal.getConnection(),sql, params.toArray());

    }

    /**
     * 通过用户id查找所有id
     * @param userid
     * @return
     * @throws SQLException
     */
    public List<Order> findOrdersByUserId(String userid) throws SQLException {
        String sql = "select * from orders where user_id = ?";
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        return qr.query(sql,new BeanListHandler<Order>(Order.class),userid);

    }

    /**
     * 通过订单id查询订单数据
     *
     * 如果模型里有模型，需要自己封装
     */
    public static Order findOrderByOrderId(String orderId) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        //1.查询order表
        String sql1 = "select * from orders where id = ?";

        Order order = qr.query(sql1, new BeanHandler<Order>(Order.class),orderId);

        //2.查询OrderItem表，把数据封装到Order的items属性

        String sql2 = "select o.*,p.name,p.price from orderitem o,products p WHERE o.product_id = p.id and order_id = ?";
        //自己封装数据
        List<OrderItem> mItems = qr.query(sql2, new ResultSetHandler<List<OrderItem>>() {

            @Override
            public List<OrderItem> handle(ResultSet rs) throws SQLException {
                //1创建集合对象
                List<OrderItem> items = new ArrayList<OrderItem>();

                //2.遍历
                while(rs.next()){
                    //创建Orderitem对象
                    OrderItem item = new OrderItem();
                    item.setBuynum(rs.getInt("buynum"));

                    //创建Product对象
                    Product p = new Product();
                    p.setId(rs.getInt("product_id"));
                    p.setName(rs.getString("name"));
                    p.setPrice(rs.getDouble("price"));

                    //把Product放到item
                    item.setProduct(p);

                    //把item放到items
                    items.add(item);

                }
                return items;
            }
        },orderId);

        //把items放在order里面
        order.setItems(mItems);
        return  order;
    }




}
