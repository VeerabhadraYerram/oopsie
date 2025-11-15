package com.propertyrecommender.model;

import java.io.Serializable;
import java.util.Objects;

public class Property implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer propertyId;
    private Integer ownerId; // user id
    private String title;
    private String description;
    private String locality;
    private int bhk;
    private double price;
    private boolean available;

    public Property() {}

    // getters & setters
    public Integer getPropertyId() { return propertyId; }
    public void setPropertyId(Integer propertyId) { this.propertyId = propertyId; }
    public Integer getOwnerId() { return ownerId; }
    public void setOwnerId(Integer ownerId) { this.ownerId = ownerId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocality() { return locality; }
    public void setLocality(String locality) { this.locality = locality; }
    public int getBhk() { return bhk; }
    public void setBhk(int bhk) { this.bhk = bhk; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return String.format("Property[id=%d, title=%s, locality=%s, bhk=%d, price=%.2f, available=%s]",
                propertyId, title, locality, bhk, price, available);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Property)) return false;
        return Objects.equals(propertyId, ((Property)o).propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId);
    }
}
