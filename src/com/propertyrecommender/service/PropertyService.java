package com.propertyrecommender.service;

import com.propertyrecommender.dao.PropertyDAO;
import com.propertyrecommender.model.Property;

import java.util.List;

public class PropertyService {
    private final PropertyDAO dao = new PropertyDAO();

    public Property addProperty(Property p) { return dao.save(p); }
    public Property updateProperty(Property p) { return dao.save(p); }
    public Property findById(int id) { return dao.findById(id); }
    public List<Property> listAll() { return dao.findAll(); }
    public boolean remove(int id) { return dao.delete(id); }

    // Method overloading example: search with different parameters
    public List<Property> search(String locality, double maxPrice) {
        return dao.findByLocalityAndBudget(locality, maxPrice);
    }

    public List<Property> search(String locality) {
        return dao.findByLocalityAndBudget(locality, Double.MAX_VALUE);
    }
}
