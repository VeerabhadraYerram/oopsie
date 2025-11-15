package com.propertyrecommender.service;

import com.propertyrecommender.dao.UserDAO;
import com.propertyrecommender.model.User;

import java.util.List;

public class AdminService {
    private final UserDAO userDAO = new UserDAO();

    public List<User> listUsers() { return userDAO.findAll(); }

    public boolean deleteUser(int id) {
        return userDAO.delete(id);
    }
}
