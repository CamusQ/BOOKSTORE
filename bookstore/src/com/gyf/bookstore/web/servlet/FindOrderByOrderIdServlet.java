package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.model.Order;
import com.gyf.bookstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 订单详情
 */
@WebServlet("/findOrderByOrderId")
public class FindOrderByOrderIdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取orderid
        String orderId = request.getParameter("orderId");

        //2.调用service
        OrderService os = new OrderService();
        Order order = os.findOrdersByOrderId(orderId);

        //3.转发到orderInfo.jsp
        request.setAttribute("order", order);
        request.getRequestDispatcher("/orderInfo.jsp").forward(request, response);
    }
}
