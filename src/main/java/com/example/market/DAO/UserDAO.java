package com.example.market.DAO;

import com.example.market.Entities.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAO {

    private final JdbcTemplate jdbcTemplate;

    public UserDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<User> findByUsername(String username) {
        try {
            String sql = "SELECT * FROM get_user_by_login(?)";
            User user = jdbcTemplate.queryForObject(
                    sql,
                    (rs, rowNum) -> new User(
                            rs.getString("r_user_login"),
                            rs.getString("r_user_password"),
                            rs.getString("r_user_role"),
                            rs.getString("r_email")
                    ),
                    username
            );
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void saveUser(User user) {
        jdbcTemplate.update(
                "CALL save_user(?, ?, ?)",
                user.getLogin(),
                user.getPassword(),
                user.getEmail()
        );
    }
}
