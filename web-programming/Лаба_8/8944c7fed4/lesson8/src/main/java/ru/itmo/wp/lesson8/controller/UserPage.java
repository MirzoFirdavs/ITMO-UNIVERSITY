package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.lesson8.domain.User;
import ru.itmo.wp.lesson8.service.UserService;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable String id, Model model) {
        User user = null;
        if (isLong(id)) {
            user = userService.findById(Long.parseLong(id));
        }

        model.addAttribute("profile", user);
        return "UserPage";
    }

    @GetMapping("/user/")
    public String emptyUser(Model model) {
        model.addAttribute("profile", null);
        return "UserPage";
    }
}
