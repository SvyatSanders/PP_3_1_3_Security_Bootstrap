package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
public class UserController {

    @GetMapping("/user")
    public String userList(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("authorisedUser", user);
        return "userController/user";
    }


//    @PostMapping("/admin")
//    public String deleteUser(@RequestParam(required = true, defaultValue = "") Long id,
//                             @RequestParam(required = true, defaultValue = "") String action,
//                             Model model) {
//        if (action.equals("delete")) {
//            userService.deleteUser(id);
//        }
//        return "redirect:/admin";
//    }
//
//    @GetMapping("admin/gt/@{id}")
//    public String gtUser(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(id));
//        return "admin";
//    }
}
