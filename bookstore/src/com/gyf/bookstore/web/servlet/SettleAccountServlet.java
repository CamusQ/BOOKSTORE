package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 结账功能
 */

@WebServlet("/settleAccount")
public class SettleAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //判断当前浏览器是否有登录
        User user = (User) request.getSession().getAttribute("user");
        //如果有登陆进入订单页面
        if (user != null) {
            response.sendRedirect(request.getContextPath() + "/order.jsp");
        } else {
            //如果没有登录先进行登录
            response.sendRedirect(request.getContextPath() + "/login.jsp");

        }
    }
}
