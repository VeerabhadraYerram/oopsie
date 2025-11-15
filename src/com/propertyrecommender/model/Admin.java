package com.propertyrecommender.model;

public class Admin extends User {
	private static final long serialVersionUID = 1L;

    public Admin() { super(); }
    public Admin(Integer id, String username, String password, String fullName, String email) {
        super(id, username, password, fullName, email, "ADMIN");
    }

    // Admin-specific methods can be added here.
}