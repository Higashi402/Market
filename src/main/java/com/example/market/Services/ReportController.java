package com.example.market.Services;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class ReportController {

    @Autowired
    private Birt birt;

    @GetMapping("/report-orders")
    public String report() {
        return "report";
    }

    @PostMapping("/report-orders")
    public void report(Principal principal, HttpServletResponse response, HttpServletRequest request) {
        String username = principal.getName();
        birt.generateOrdersPDF(username, response, request);
    }


    @GetMapping("/report-products")
    public String reportProducts() {
        return "report";
    }

    @PostMapping("/report-products")
    public void reportProducts(HttpServletResponse response, HttpServletRequest request) {
        birt.generateProductsPDF(response, request);
    }
}


