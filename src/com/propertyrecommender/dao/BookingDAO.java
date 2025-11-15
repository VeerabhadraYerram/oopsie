package com.propertyrecommender.dao;

import com.propertyrecommender.model.Booking;
import com.propertyrecommender.util.JDBCUtil;
import com.propertyrecommender.exception.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO implements GenericDAO<Booking, Integer> {

    @Override
    public Booking save(Booking b) {
        String sql = (b.getBookingId() == null)
            ? "INSERT INTO bookings(property_id,user_id,booking_date,status,amount) VALUES(?,?,?,?,?)"
            : "UPDATE bookings SET property_id=?, user_id=?, booking_date=?, status=?, amount=? WHERE booking_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, b.getPropertyId());
            ps.setInt(2, b.getUserId());
            ps.setTimestamp(3, b.getBookingDate() == null ? new Timestamp(System.currentTimeMillis()) : b.getBookingDate());
            ps.setString(4, b.getStatus());
            ps.setDouble(5, b.getAmount());
            if (b.getBookingId() != null) ps.setInt(6, b.getBookingId());
            ps.executeUpdate();
            if (b.getBookingId() == null) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) b.setBookingId(rs.getInt(1));
            }
            return b;
        } catch (SQLException e) {
            throw new DataAccessException("Error saving booking", e);
        }
    }

    @Override
    public Booking findById(Integer id) {
        String sql = "SELECT * FROM bookings WHERE booking_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching booking", e);
        }
    }

    @Override
    public List<Booking> findAll() {
        String sql = "SELECT * FROM bookings";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Booking> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching bookings", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM bookings WHERE booking_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting booking", e);
        }
    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setBookingId(rs.getInt("booking_id"));
        b.setPropertyId(rs.getInt("property_id"));
        b.setUserId(rs.getInt("user_id"));
        b.setBookingDate(rs.getTimestamp("booking_date"));
        b.setStatus(rs.getString("status"));
        b.setAmount(rs.getDouble("amount"));
        return b;
    }
}
