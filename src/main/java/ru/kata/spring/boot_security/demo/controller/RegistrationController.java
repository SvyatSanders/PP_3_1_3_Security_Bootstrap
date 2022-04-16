package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class RegistrationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        System.out.println("начинает работать метод public String registration");
        String roleUser = null;
        String roleAdmin = null;
        model.addAttribute("userForm", new User());
        model.addAttribute("roleUser", roleUser);
        model.addAttribute("roleAdmin", roleAdmin);

        return "registrationController/registration";
    }

//    @PostMapping("/registration")
//    public String addUser(@ModelAttribute("userForm") User userForm, String roleUser, String roleAdmin, Model model) {
//        System.out.println("начинает работать метод public String addUser");
//        System.out.println("roleUser = " + roleUser);
//        System.out.println("roleAdmin = " + roleAdmin);
//
//        if (!userService.saveUser(userForm, roleUser, roleAdmin)) {
//            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
//            return "registrationController/registration";
//        }
//
//        return "redirect:/admin/";
//    }
}
