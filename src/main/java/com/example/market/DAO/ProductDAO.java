package com.example.market.DAO;

import com.example.market.DTO.ProductFilterDTO;
import com.example.market.Entities.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM nomenclature";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public List<Product> filterProducts(ProductFilterDTO filter) {
        StringBuilder sql = new StringBuilder("SELECT * FROM nomenclature WHERE 1=1");
        List<Object> params = new java.util.ArrayList<>();

        if (filter.getMinPrice() != 0) {
            sql.append(" AND price >= ?");
            params.add(filter.getMinPrice());
        }
        if (filter.getMaxPrice() != 0) {
            sql.append(" AND price <= ?");
            params.add(filter.getMaxPrice());
        }
        if (filter.getDeliveryTime() != null) {
            sql.append(" AND delivery_date <= ?");
            params.add(filter.getDeliveryTime());
        }

        return jdbcTemplate.query(sql.toString(), params.toArray(), productRowMapper);
    }

    public Product findById(String articleNumber) {
        String sql = "SELECT * FROM nomenclature WHERE article_number = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{articleNumber}, productRowMapper);
    }

    public List<Product> searchProducts(String query) {
        String sql = "SELECT * FROM nomenclature WHERE LOWER(title) LIKE LOWER(?)";
        String searchPattern = "%" + query + "%";
        return jdbcTemplate.query(sql, new Object[]{searchPattern}, productRowMapper);
    }


    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getString("article_number"),
            rs.getString("title"),
            rs.getString("manufacturer"),
            rs.getString("description"),
            rs.getDouble("price"),
            rs.getInt("image"),
            rs.getInt("delivery_date")
    );
}
