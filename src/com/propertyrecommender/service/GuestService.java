package com.propertyrecommender.service;

public class GuestService {
    private final PropertyService propertyService = new PropertyService();

    public void showLatestProperties() {
        propertyService.listAll().stream().limit(10).forEach(System.out::println);
    }
}
