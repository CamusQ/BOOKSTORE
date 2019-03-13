package com.gyf.bookstore.web.servlet;


import com.gyf.bookstore.model.Product;
import com.gyf.bookstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/addCart")
public class AddCartServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id、
        String id = request.getParameter("id");

        //2.通过id查询数据库对应商品dao/service
        ProductService ps = new ProductService();
        Product p = ps.findBook(id);

        //3.把购买的商品放在购物车
        //3.1先从session获取购物车数据
        Map<Product,Integer> cart = (Map<Product,Integer>)request.getSession().getAttribute("cart");

        //3.2如果没有购物车数据就创建一个map对象
        if(cart == null){
            cart = new HashMap<Product,Integer>();
            cart.put(p, 1);
        }else{

            //3.3判断map里面是否有当前想购物的商品
            if(cart.containsKey(p)){
                cart.put(p, cart.get(p) + 1);
            }else{
                cart.put(p, 1);
            }
        }

        //4.打印购物车的数据
        for(Map.Entry<Product,Integer> entry : cart.entrySet()){
            System.out.println(entry.getKey() + "数量" + entry.getValue());
        }

        //5.存session
        request.getSession().setAttribute("cart", cart);

        //6.响应客户端
        //继续购物,查看购物车
        String a1 = "<a href=\""+ request.getContextPath() +"/showProductByPage\">继续购物</a>";
        String a2 = "<br><a href=\""+ request.getContextPath() +"/cart.jsp\">查看购物车</a>";
        response.getWriter().write(a1);
        response.getWriter().write(a2);


    }
}
