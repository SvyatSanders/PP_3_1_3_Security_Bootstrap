package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String userList(Model model) {
        model.addAttribute("usersList", userService.getAllUsers());
        for (User user : userService.getAllUsers()) {
            System.out.println("from public String userList(Model model) " + user.getUsername());
        }
        return "adminController/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User userToEdit = userService.getUserById(id);
        String roleUser = (userToEdit.getStringRoles().contains("ROLE_USER") ? "on" : null);
        String roleAdmin = (userToEdit.getStringRoles().contains("ROLE_ADMIN") ? "on" : null);

        model.addAttribute("userToEdit", userService.getUserById(id));
        model.addAttribute("roleUser", roleUser);
        model.addAttribute("roleAdmin", roleAdmin);
        return "adminController/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, String roleUser, String roleAdmin) {
        userService.updateUser(user, roleUser, roleAdmin);
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

    @GetMapping("gt/@{id}")
    public String gtUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("allUsers", userService.usergtList(id));
        return "adminController/admin";
    }

}
