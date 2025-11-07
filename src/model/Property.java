package model;

import java.util.Objects;

public class Property {
    private final String id;
    private final String title;
    private final String city;
    private final String neighborhood;
    private final String propertyType; // e.g., "apartment", "house", "studio"
    private final int bedrooms;
    private final int bathrooms;
    private final boolean petFriendly;
    private final boolean furnished;
    private final double pricePerMonth;
    private final double sizeSqFt;
    private final double rating; // 0.0 - 5.0

    public Property(
            String id,
            String title,
            String city,
            String neighborhood,
            String propertyType,
            int bedrooms,
            int bathrooms,
            boolean petFriendly,
            boolean furnished,
            double pricePerMonth,
            double sizeSqFt,
            double rating
    ) {
        this.id = id;
        this.title = title;
        this.city = city;
        this.neighborhood = neighborhood;
        this.propertyType = propertyType;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.petFriendly = petFriendly;
        this.furnished = furnished;
        this.pricePerMonth = pricePerMonth;
        this.sizeSqFt = sizeSqFt;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public int getBedrooms() {
        return bedrooms;
    }

    public int getBathrooms() {
        return bathrooms;
    }

    public boolean isPetFriendly() {
        return petFriendly;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public double getPricePerMonth() {
        return pricePerMonth;
    }

    public double getSizeSqFt() {
        return sizeSqFt;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Property property = (Property) o;
        return Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Property{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", city='" + city + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", type='" + propertyType + '\'' +
                ", bedrooms=" + bedrooms +
                ", bathrooms=" + bathrooms +
                ", petFriendly=" + petFriendly +
                ", furnished=" + furnished +
                ", price=" + pricePerMonth +
                ", sizeSqFt=" + sizeSqFt +
                ", rating=" + rating +
                '}';
    }
}


