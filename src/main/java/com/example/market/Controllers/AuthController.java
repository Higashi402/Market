package com.example.market.Controllers;

import com.example.market.DAO.UserDAO;
import com.example.market.Entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserDAO userDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String login, @RequestParam("password") String password, @RequestParam("email") String email, Model model) {
        if (userDAO.findByUsername(login).isPresent()) {
            model.addAttribute("error", "Пользователь уже существует");
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(login, encodedPassword,"user",email);
        userDAO.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
