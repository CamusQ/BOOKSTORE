package com.gyf.bookstore.web.servlet;


import com.gyf.bookstore.model.Product;
import com.gyf.bookstore.service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/productInfo")
public class ProductInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取id、
        String id = request.getParameter("id");

        //2.通过id查询数据库对应商品dao/service
        ProductService ps = new ProductService();
        Product product = ps.findBook(id);

        //3.把商品存在request，跳转到product_info.jsp进行数据展示
        request.setAttribute("product", product);
        request.getRequestDispatcher("/product_info.jsp").forward(request, response);
    }
}
