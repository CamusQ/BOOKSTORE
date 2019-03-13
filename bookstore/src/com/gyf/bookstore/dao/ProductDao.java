package com.gyf.bookstore.dao;

import com.gyf.bookstore.model.Product;
import com.gyf.bookstore.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    /**
     * 计算总记录数
     * 如果category是null，是查表的所有记录数
     */
    public long count(String category) throws SQLException {
        //1.定义记录数
        long count = 0;
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select count(*) from products where 1=1";

        if (category != null && !"".equals(category)) {
            sql += " and category = ?";
            count = (long)qr.query(sql, new ScalarHandler(),category);
        } else {

            count = (long)qr.query(sql, new ScalarHandler());
        }
        return count;
    }

    /**
     *
     * @param category 类型
     * @param page 当前页
     * @param pageSize 每页数量
     * @return
     */
    public List<Product> findBooks(String category, int page,int pageSize) throws SQLException {

        //拼接SQL和参数
        String sql = "select * from products where 1=1";

        List<Object> prams = new ArrayList<Object>();

        if(category != null || "".equals(category)){
            sql += " and category = ?";
            prams.add(category);
        }
        sql += " limit ?,?";

        int start = (page - 1) * pageSize;
        int length = pageSize;

        prams.add(start);
        prams.add(length);

        //执行
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        return qr.query(sql, new BeanListHandler<Product>(Product.class),prams.toArray());

    }


    /**
     * 通过id查找商品
     * @throws SQLException
     */
    public Product findBook(String id) throws SQLException {
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        return qr.query("select * from products where id = ?",
                new BeanHandler<Product>(Product.class),id);


    }

    @Test
    public void test() throws SQLException {

        ProductDao dao = new ProductDao();
//        long count = dao.count(null);
        String category = "计算机";
        long count = dao.count(category);

        List<Product> books = dao.findBooks(category,1,4);

        for (Product b : books){

            System.out.println(b);
        }

    }
}
