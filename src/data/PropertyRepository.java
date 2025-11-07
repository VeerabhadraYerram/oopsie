package data;

import model.Property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyRepository {
    private final List<Property> properties;

    public PropertyRepository() {
        this.properties = new ArrayList<>();
        seed();
    }

    public List<Property> getAll() {
        return Collections.unmodifiableList(properties);
    }

    private void seed() {
        // City A: Metropolis
        properties.add(new Property("P-001", "Cozy Downtown Studio", "Metropolis", "Downtown", "studio", 0, 1, false, true, 1200, 400, 4.2));
        properties.add(new Property("P-002", "Sunny 1BR near Park", "Metropolis", "Parkside", "apartment", 1, 1, true, false, 1500, 650, 4.6));
        properties.add(new Property("P-003", "Modern 2BR Loft", "Metropolis", "Warehouse", "apartment", 2, 2, true, true, 2400, 900, 4.8));
        properties.add(new Property("P-004", "Spacious Family Home", "Metropolis", "Suburbia", "house", 3, 2, true, false, 3200, 1800, 4.4));

        // City B: Gotham
        properties.add(new Property("P-101", "Charming Brownstone", "Gotham", "Old Town", "house", 2, 2, false, false, 2200, 1400, 4.1));
        properties.add(new Property("P-102", "High-Rise 1BR", "Gotham", "Midtown", "apartment", 1, 1, true, true, 2000, 700, 4.5));
        properties.add(new Property("P-103", "Budget Basement Studio", "Gotham", "Industrial", "studio", 0, 1, false, false, 900, 350, 3.6));

        // City C: Star City
        properties.add(new Property("P-201", "Waterfront Condo", "Star City", "Marina", "apartment", 2, 2, true, true, 2800, 1000, 4.9));
        properties.add(new Property("P-202", "Suburban 3BR", "Star City", "Greenview", "house", 3, 2, true, false, 2500, 1600, 4.3));
        properties.add(new Property("P-203", "Compact Studio", "Star City", "Central", "studio", 0, 1, false, true, 1100, 380, 4.0));
    }
}


