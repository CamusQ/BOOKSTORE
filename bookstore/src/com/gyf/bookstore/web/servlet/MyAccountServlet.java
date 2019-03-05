package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/myAccount")
public class MyAccountServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            User user = (User) request.getSession().getAttribute("user");
        //如果登陆进人myAccount.jsp

        if(user != null){
            response.sendRedirect(request.getContextPath() + "/myAccount.jsp");
        }else{
            //如果未登录，进入login.jsp
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }
}
