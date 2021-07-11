package com.wyp.service.Impl;

import com.wyp.dao.UserDAO;
import com.wyp.entity.User;
import com.wyp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author WYP
 * @date 2021-07-11 10:00
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDAO userDAO;

    @Override
    public User login(User user) {
        return userDAO.login(user);
    }
}
