package com.betaplan.eva.loginandregistration.controllers;

import com.betaplan.eva.loginandregistration.models.LoginUser;
import com.betaplan.eva.loginandregistration.models.User;
import com.betaplan.eva.loginandregistration.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.management.modelmbean.ModelMBean;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
@Controller
public class UserController {



        @Autowired
        private UserService userServ;

        @GetMapping("/")
        public String index(Model model) {

            model.addAttribute("newUser", new User());
            model.addAttribute("newLogin", new LoginUser());
            return "index";
        }

        @PostMapping("/register")
        public String register(@Valid @ModelAttribute("newUser") User newUser,
                               BindingResult result, Model model, HttpSession session) {

            userServ.userRegister(newUser, result);

            if (result.hasErrors()) {
                // Be sure to send in the empty LoginUser before
                // re-rendering the page.
                model.addAttribute("newLogin", new LoginUser());
                return "index";
            }

            session.setAttribute("userId", newUser.getId());

            return "redirect:/welcome";
        }

        @PostMapping("/login")
        public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
                            BindingResult result, Model model, HttpSession session) {

            // Add once service is implemented:
            User user = userServ.login(newLogin, result);

            if (result.hasErrors()) {
                model.addAttribute("newUser", new User());
                return "index";
            }

            session.setAttribute("userId", user.getId());

            return "redirect:/welcome";
        }

        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.setAttribute("userId", null);
            return "redirect:/";
        }

        @GetMapping("/welcome")
        public String welcome(Model model, HttpSession session) {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return "redirect:/";
            }
            User user = userServ.findById(userId);
            model.addAttribute("user", user);
            return "dashboard";
        }
    }

