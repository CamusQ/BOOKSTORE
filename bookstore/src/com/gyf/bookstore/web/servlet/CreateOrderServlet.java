package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.model.Order;
import com.gyf.bookstore.model.OrderItem;
import com.gyf.bookstore.model.Product;
import com.gyf.bookstore.model.User;
import com.gyf.bookstore.service.OrderService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 生成订单
 */
@WebServlet("/createOrder")
public class CreateOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取session的user
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.getWriter().write("非法访问");
            return;
        }

        //获取购物车
        Map<Product,Integer> cart = (Map<Product,Integer>)request.getSession().getAttribute("cart");
        if(cart == null || cart.size() == 0){
            response.getWriter().write("购物车没有商品");
            return;
        }


        //1.把数据封装好
        Order order = new Order();
        try {
            //1.1把请求参数封装成order
            BeanUtils.populate(order,request.getParameterMap());

            //1.2补全Order数据
            order.setId(UUID.randomUUID().toString());
            order.setOrdertime(new Date());
            order.setUser(user);

            //1.3封装订单的详情
            List<OrderItem > items = new ArrayList<OrderItem>();
            //取购物车
            double totalPrice = 0;
            for(Map.Entry<Product,Integer> entry : cart.entrySet()){
                OrderItem item = new OrderItem();
                //设置购物数量
                item.setBuynum(entry.getValue());
                //设置商品
                item.setProduct(entry.getKey());
                //设置订单
                item.setOrder(order);

                totalPrice += entry.getKey().getPrice() * entry.getValue();

                items.add(item);
            }
            //设置Order中items
            order.setItems(items);


            //1.4设置总价格
            order.setMoney(totalPrice);

            System.out.println("-----------");
            System.out.println("订单：" );
            System.out.println(order);

            System.out.println("订单中商品：");
            for (OrderItem item : items){
                System.out.println("商品名称：" + item.getProduct().getName());
            }


            //2.插入数据库
            OrderService os = new OrderService();
            os.creatOrder(order);

            //3.订单成功（移除购物车数据）
            request.getSession().removeAttribute("cart");


            //4.响应：
            response.getWriter().write("下单成功....");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
