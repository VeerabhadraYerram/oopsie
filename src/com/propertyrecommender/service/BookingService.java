package com.propertyrecommender.service;

import com.propertyrecommender.dao.BookingDAO;
import com.propertyrecommender.dao.PropertyDAO;
import com.propertyrecommender.model.Booking;
import com.propertyrecommender.model.Property;

import java.sql.Timestamp;
import java.util.List;

public class BookingService {
    private final BookingDAO bookingDAO = new BookingDAO();
    private final PropertyDAO propertyDAO = new PropertyDAO();

    public Booking bookProperty(int propertyId, int userId, double amount) {
        Property p = propertyDAO.findById(propertyId);
        if (p == null || !p.isAvailable()) throw new RuntimeException("Property not available");
        Booking b = new Booking();
        b.setPropertyId(propertyId);
        b.setUserId(userId);
        b.setBookingDate(new Timestamp(System.currentTimeMillis()));
        b.setStatus("BOOKED");
        b.setAmount(amount);
        Booking saved = bookingDAO.save(b);
        // set property unavailable
        p.setAvailable(false);
        propertyDAO.save(p);
        return saved;
    }

    public boolean cancelBooking(int bookingId) {
        Booking b = bookingDAO.findById(bookingId);
        if (b == null || "CANCELLED".equals(b.getStatus())) return false;
        b.setStatus("CANCELLED");
        bookingDAO.save(b);
        // make property available
        Property p = propertyDAO.findById(b.getPropertyId());
        if (p != null) { p.setAvailable(true); propertyDAO.save(p); }
        return true;
    }

    public List<Booking> listAllBookings() { return bookingDAO.findAll(); }
}
