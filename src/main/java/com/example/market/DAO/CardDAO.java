package com.example.market.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CardDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CardDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getFirstCardNumber(String username) {
        try {
            return jdbcTemplate.queryForObject(
                    "SELECT get_user_card_number(?)",
                    String.class,
                    username
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
