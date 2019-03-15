package com.gyf.bookstore.web.servlet;

import com.gyf.bookstore.model.Product;
import com.gyf.bookstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 更新购物车数量
 */
@WebServlet("/changeNum")
public class ChangeNumServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取参数
        String id = request.getParameter("id");
        String num = request.getParameter("num");

        //2.更新购物车数据
        //2.1通过id产找商品
        ProductService ps = new ProductService();
        Product p = ps.findBook(id);

        //2.2通过商品更改session数据
        Map<Product,Integer> cart =(Map<Product,Integer>) request.getSession().getAttribute("cart");

        //替换
        if(cart.containsKey(p)){//判断是否有这个商品
            if("0".equals(num)){//移除商品
                cart.remove(p);
            }else{
                cart.put(p,Integer.parseInt(num));
            }
        }

        //重新保存到session
        request.getSession().setAttribute("cart", cart);

        //回到购物车页面
        response.sendRedirect(request.getContextPath() + "/cart.jsp");

    }
}
