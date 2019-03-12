package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.exception.UserException;
import com.gyf.bookstore.model.User;
import com.gyf.bookstore.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserById")
public class FindUserByIdServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取参数
        String userid = request.getParameter("id");

        //2.从数据库查找
        UserService us = new UserService();
        try {
            User user = us.findUserById(userid);
            /**
             * el表达式取数据顺序：page,request,session,application
             */
            //3.放在request
            request.setAttribute("u",user);
            //4.回到modifyuserinfo.jsp
            request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request, response);

        } catch (UserException e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
        }


    }
}
