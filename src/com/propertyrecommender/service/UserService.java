package com.propertyrecommender.service;

import com.propertyrecommender.dao.UserDAO;
import com.propertyrecommender.model.User;

public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public User findByUsername(String username) { return userDAO.findByUsername(username); }
    public User updateProfile(User u) { return userDAO.save(u); }
}
