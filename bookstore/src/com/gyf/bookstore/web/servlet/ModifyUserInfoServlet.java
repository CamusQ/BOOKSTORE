package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.model.User;
import com.gyf.bookstore.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/modifyUserInfo")
public class ModifyUserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取表单的参数
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
            System.out.println(user);

            //2.修改
            UserService us = new UserService();
            us.modifyUserInfo(user);

            //3.跳转
            response.sendRedirect(request.getContextPath() + "/modifyUserInfoSuccess.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
        }
        //2.
    }
}
