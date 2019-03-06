package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.exception.UserException;
import com.gyf.bookstore.model.User;
import com.gyf.bookstore.service.UserService;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //1.校验验证码
        //获取表单的验证码

        //1.把参数转成Bean,model
        User user = new User();
        try {
            BeanUtils.populate(user, request.getParameterMap());
            System.out.println(user);

            //给无数据的属性赋值
            user.setActiveCode(UUID.randomUUID().toString());//激活码
            user.setRole("普通用户");//角色
            user.setRegistTime(new Date());
            System.out.println(user);

            //2.注册
            UserService us = new UserService();
            us.register(user);

            //3.返回结果

            //3.1成功-进入成功界面
            request.getRequestDispatcher("/registersuccess.jsp").forward(request, response);
        } catch (UserException e) {
            //3.2注册-进入注册页面
            e.printStackTrace();
            request.setAttribute("register_err", e.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("转模型失败......");
            e.printStackTrace();
        }


    }
}
