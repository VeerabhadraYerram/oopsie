package com.propertyrecommender.model;

public class Guest extends User {
	private static final long serialVersionUID = 1L;

    public Guest() {
        super(null, "guest", "", "Guest User", "", "GUEST");
    }
}
