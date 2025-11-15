package com.propertyrecommender.service;

import com.propertyrecommender.dao.UserDAO;
import com.propertyrecommender.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        User u = userDAO.findByUsername(username);
        if (u != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    public User register(User u) {
        // basic validation
        if (userDAO.findByUsername(u.getUsername()) != null) throw new RuntimeException("Username exists");
        return userDAO.save(u);
    }
}
