package com.propertyrecommender.dao;

import com.propertyrecommender.model.User;
import com.propertyrecommender.util.JDBCUtil;
import com.propertyrecommender.exception.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements GenericDAO<User, Integer> {

    @Override
    public User save(User u) {
        String sql = (u.getUserId() == null)
            ? "INSERT INTO users(username,password,full_name,email,role) VALUES(?,?,?,?,?)"
            : "UPDATE users SET username=?, password=?, full_name=?, email=?, role=? WHERE user_id=?";
        try (Connection conn = JDBCUtil.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getRole());
            if (u.getUserId() != null) ps.setInt(6, u.getUserId());
            ps.executeUpdate();
            if (u.getUserId() == null) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) { u.setUserId(rs.getInt(1)); }
            }
            return u;
        } catch (SQLException e) {
            throw new DataAccessException("Error saving user", e);
        }
    }

    @Override
    public User findById(Integer id) {
        String sql = "SELECT * FROM users WHERE user_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching user", e);
        }
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching user", e);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<User> list = new ArrayList<>();
            while (rs.next()) list.add(mapRow(rs));
            return list;
        } catch (SQLException e) {
            throw new DataAccessException("Error fetching users", e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM users WHERE user_id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error deleting user", e);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        u.setFullName(rs.getString("full_name"));
        u.setEmail(rs.getString("email"));
        u.setRole(rs.getString("role"));
        return u;
    }
}
