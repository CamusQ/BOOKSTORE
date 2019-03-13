package com.gyf.bookstore.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")//拦截所有的请求路径
public class MyEncodingFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}
    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        //解决响应乱码
        HttpServletResponse myResponse = (HttpServletResponse) response;
        myResponse.setHeader( "content-type", "text/html;charset=utf-8");


        //1.设置POST请求中文乱码的问题
        HttpServletRequest hsr = (HttpServletRequest)request;
        if(hsr.getMethod().equalsIgnoreCase("post")){
            request.setCharacterEncoding("UTF-8");
        }
        chain.doFilter(request, response);
    }
}

