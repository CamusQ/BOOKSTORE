package com.gyf.bookstore.service;

import com.gyf.bookstore.dao.UserDao;
import com.gyf.bookstore.exception.UserException;
import com.gyf.bookstore.model.User;
import com.gyf.bookstore.utils.SendJMail;

import java.sql.SQLException;

public class UserService {

    //创建Dao
    UserDao userDao = new UserDao();

    public void register(User user) throws UserException {
        try {
            //网数据库添加用户
            userDao.addUser(user);

            //项目发布时，要改成域名
            String link = "http://localhost:8080/bookstore/active?activeCode=" + user.getActiveCode();
            String html = "<a href=\"" + link + "\"> 欢迎你注册网上书城账号，请点击激活</a>";
            //发送激活邮件
            SendJMail.sendMail(user.getEmail(), html);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("用户注册失败，用户名重复");
        }

    }

    public void activeUser(String activeCode) throws UserException {

        //1.查询激活码的用户是否存在
        try {
            User user = userDao.findUserByActiveCode(activeCode);
            if (user == null) {
                throw new UserException("非法激活，用户不存在");
            }
            if (user != null && user.getState() == 1) {
                throw new UserException("用户已激活过了....");
            }
            //激活用户
            userDao.updateState(activeCode);
        } catch (SQLException e) {
            throw new UserException("激活失败");
        }
    }

    public User login(String username, String password) throws UserException {
        //1.查询
        User user = null;
        try {
            user = userDao.findUserByUsernameAndPassword(username, password);


            //2.判断
            if (user == null) {
                throw new UserException("用户名或者密码不正确");
            }

            if (user.getState() == 0) {
                throw new UserException("用户未激活，请先登录邮箱进行激活");
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("登录失败");
        }
    }

    public User findUserById(String id) throws UserException {
        //1.查询
        User user = null;
        try {
            user = userDao.findUserById(id);


            //2.判断
            if (user == null) {
                throw new UserException("用户名或者密码不正确");
            }

            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("未知错误");
        }
    }


    public void modifyUserInfo(User user) throws UserException {
        //1.查询
        try {

            userDao.updateUser(user);

        } catch (SQLException e) {
            throw new UserException("未知错误");
        }
    }
}
