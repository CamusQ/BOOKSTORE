package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.model.Order;
import com.gyf.bookstore.model.User;
import com.gyf.bookstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 查询订单，通过用户的id查找订单
 *
 * 1.dao
 * 2.service
 * 3.servlet调用service
 */
@WebServlet("/findOrderById")
public class FindOrderByIdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取用户id
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            response.getWriter().write("非法访问...");
            return;
        }

        //2.调用service
        OrderService os = new OrderService();
        List<Order> orders = os.findOrdersByUserId(user.getId() + "");

        //3.存数据在request
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/orderlist.jsp").forward(request, response);

    }
}
