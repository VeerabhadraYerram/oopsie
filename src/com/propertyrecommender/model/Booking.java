package com.propertyrecommender.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Booking implements Serializable {
	private static final long serialVersionUID = 1L;

    private Integer bookingId;
    private Integer propertyId;
    private Integer userId;
    private Timestamp bookingDate;
    private String status; // BOOKED, CANCELLED
    private double amount;

    // getters & setters
    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }
    public Integer getPropertyId() { return propertyId; }
    public void setPropertyId(Integer propertyId) { this.propertyId = propertyId; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public java.sql.Timestamp getBookingDate() { return bookingDate; }
    public void setBookingDate(java.sql.Timestamp bookingDate) { this.bookingDate = bookingDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    @Override
    public String toString() {
        return String.format("Booking[id=%d, propertyId=%d, userId=%d, status=%s, amount=%.2f]",
                bookingId, propertyId, userId, status, amount);
    }
}
