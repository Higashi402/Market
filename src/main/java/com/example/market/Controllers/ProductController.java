package com.example.market.Controllers;

import com.example.market.DAO.CardDAO;
import com.example.market.DAO.OrderDAO;
import com.example.market.DAO.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.UUID;

@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductDAO productDAO;
    private final OrderDAO orderDAO;
    private final CardDAO userCardDAO;

    @Autowired
    public ProductController(ProductDAO productDAO, OrderDAO orderDAO, CardDAO cardDAO) {
        this.productDAO = productDAO;
        this.orderDAO = orderDAO;
        this.userCardDAO = cardDAO;
    }

    @GetMapping("/{id}")
    public String getProductPage(@PathVariable String id, Model model) {
        model.addAttribute("product", productDAO.findById(id));

        return "product";
    }


    @PostMapping("/{id}/order")
    public String createOrder(
            @PathVariable String id,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Для оформления заказа необходимо авторизоваться");
            return "redirect:/login";
        }

        String username = principal.getName();
        String nomenclature = productDAO.findById(id).getArticleNumber();

        try {
            String cardNumber = userCardDAO.getFirstCardNumber(username);

            if (cardNumber == null) {
                redirectAttributes.addFlashAttribute("error", "У вас нет привязанных карт");
                return "redirect:/product/" + id;
            }

            Integer orderNumber = Math.abs(UUID.randomUUID().hashCode());
            orderDAO.createOrder(
                    cardNumber,
                    orderNumber,
                    "Одобрен",
                    nomenclature,
                    new Timestamp(System.currentTimeMillis())
            );

            redirectAttributes.addFlashAttribute("success", "Заказ успешно оформлен!");
            return "redirect:/product/" + id;

        } catch (EmptyResultDataAccessException e) {
            redirectAttributes.addFlashAttribute("error", "Пользователь не найден");
            return "redirect:/product/" + id;
        } catch (DataAccessException e) {
            redirectAttributes.addFlashAttribute("error", "Ошибка базы данных: " + e.getMessage());
            return "redirect:/product/" + id;
        }
    }

}
