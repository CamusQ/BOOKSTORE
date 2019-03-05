package com.gyf.bookstore.service;

import com.gyf.bookstore.dao.UserDao;
import com.gyf.bookstore.exception.UserException;
import com.gyf.bookstore.model.User;

import java.sql.SQLException;

public class UserService {

    //创建Dao
    UserDao userDao = new UserDao();

    public void register(User user) throws UserException {
        try {
            userDao.addUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserException("用户注册失败，用户名重复");
        }

    }
}
