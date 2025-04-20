package com.example.market.Controllers;

import com.example.market.DAO.ProductDAO;
import com.example.market.DTO.ProductFilterDTO;
import com.example.market.Entities.Order;
import com.example.market.Entities.Product;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CatalogController {

    private final ProductDAO productDAO;

    ProductFilterDTO productFilterDTO = new ProductFilterDTO();

    @Autowired
    CatalogController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        List<Product> products = productDAO.getAllProducts();
        model.addAttribute("products", products);
        return "catalog";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        List<Product> foundProducts = productDAO.searchProducts(query);
        model.addAttribute("products", foundProducts);
        return "search-results";
    }

    @PostMapping("/catalog/filter")
    @ResponseBody
    public List<Product> filterProducts(@RequestBody ProductFilterDTO filter) {
        productFilterDTO = filter;
        return productDAO.filterProducts(filter);
    }
}
