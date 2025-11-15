package com.propertyrecommender.dao;

import com.propertyrecommender.model.Property;
import com.propertyrecommender.util.JDBCUtil;
import com.propertyrecommender.exception.DataAccessException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropertyDAO implements GenericDAO<Property, Integer> {

    @Override
    public Property save(Property p) {
        String sql = (p.getPropertyId() == null)
            ? "INSERT INTO properties(owner_id,title,description,locality,bhk,price,available) VALUES(?,?,?,?,?,?,?)"
            : "UPDATE properties SET owner_id=?, title=?, description=?, locality=?, bhk=?, price=?, available=? WHERE property_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getOwnerId());
            ps.setString(2, p.getTitle());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getLocality());
            ps.setInt(5, p.getBhk());
            ps.setDouble(6, p.getPrice());
            ps.setBoolean(7, p.isAvailable());
            if (p.getPropertyId() != null) ps.setInt(8, p.getPropertyId());
            ps.executeUpdate();
            if (p.getPropertyId() == null) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) p.setPropertyId(rs.getInt(1));
            }
            return p;
        } catch (SQLException e) {
            throw new DataAccessException("Error saving property", e);
        }
    }

    @Override
    public Property findById(Integer id) {
        String sql = "SELECT * FROM properties WHERE property_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching property", e);
        }
    }

    @Override
    public List<Property> findAll() {
        String sql = "SELECT * FROM properties";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Property> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching properties", e);
        }
    }

    public List<Property> findByLocalityAndBudget(String locality, double maxPrice) {
        String sql = "SELECT * FROM properties WHERE locality LIKE ? AND price <= ? AND available=1";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + locality + "%");
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            List<Property> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("Error searching properties", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM properties WHERE property_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting property", e);
        }
    }

    private Property mapRow(ResultSet rs) throws SQLException {
        Property p = new Property();
        p.setPropertyId(rs.getInt("property_id"));
        p.setOwnerId(rs.getInt("owner_id"));
        p.setTitle(rs.getString("title"));
        p.setDescription(rs.getString("description"));
        p.setLocality(rs.getString("locality"));
        p.setBhk(rs.getInt("bhk"));
        p.setPrice(rs.getDouble("price"));
        p.setAvailable(rs.getBoolean("available"));
        return p;
    }
}
