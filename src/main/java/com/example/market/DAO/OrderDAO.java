package com.example.market.DAO;

import com.example.market.Entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
public class OrderDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createOrder(String card, Integer number, String status,
                            String nomenclature, Timestamp creationDate) {
        try {
            jdbcTemplate.update(
                    "CALL create_order(?, ?, ?, ?, ?)",
                    card, number, status, nomenclature, creationDate
            );
        } catch (DataAccessException e) {
            throw new RuntimeException("Ошибка при выполнении хранимой процедуры create_order", e);
        }
    }

    public List<Order> findByUsername(String username) {
        return jdbcTemplate.query(
                "SELECT * FROM get_orders_by_username(?)",
                new OrderRowMapper(),
                username
        );
    }

    private static class OrderRowMapper implements RowMapper<Order> {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setCard(rs.getString("card"));
            order.setNumber(rs.getInt("number"));
            order.setStatus(rs.getString("status"));
            order.setNomenclature(rs.getString("nomenclature"));
            order.setCreationDate(rs.getTimestamp("creation_date"));
            order.setDeliveryDate(rs.getTimestamp("delivery_date"));
            return order;
        }
    }
}
