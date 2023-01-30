package com.betaplan.eva.loginandregistration.controllers;

import com.betaplan.eva.loginandregistration.models.Book;
import com.betaplan.eva.loginandregistration.models.LoginUser;
import com.betaplan.eva.loginandregistration.models.User;
import com.betaplan.eva.loginandregistration.repositories.BookRepository;
import com.betaplan.eva.loginandregistration.services.BookService;
import com.betaplan.eva.loginandregistration.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Controller
public class BookController {
    @Autowired
    private UserService users;
    @Autowired
    private BookService books;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String index( @ModelAttribute("newUser") User newUser, @ModelAttribute("newLogin") User newLogin,Model model ) {

        model.addAttribute("newUser", new User());
        model.addAttribute("newLogin", new LoginUser());
        return "index";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("newUser") User newUser, @NotNull BindingResult result,HttpSession session,Model model) {

        users.userRegister(newUser, result);

        if (result.hasErrors()) {
            model.addAttribute("newLogin", new LoginUser());
            return "index";
        }

        session.setAttribute("userId", newUser.getId());

        return "redirect:/home";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("newLogin") LoginUser newLogin,
                        BindingResult result, Model model, HttpSession session) {

        User user = users.login(newLogin, result);

        if (result.hasErrors()) {
            model.addAttribute("newUser", new User());
            return "index";
        }
        session.setAttribute("userId", user.getId());

        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {

        if(session.getAttribute("userId") == null) {
            return "redirect:/";
        }

        model.addAttribute("books", books.all());
        model.addAttribute("user", users.findById((Long)session.getAttribute("userId")));
        return "home";
    }

    @GetMapping("/addPage")
    public String addPage(@ModelAttribute("book") Book book, Model model, HttpSession session) {

        User user = users.findById((Long)session.getAttribute("userId"));
        model.addAttribute("user", user);

        return "addPage";
    }

    @PostMapping("/books")
    public String createBook(@Valid @ModelAttribute("book") Book book, @NotEmpty BindingResult result) {

        if (result.hasErrors()) {
            return "addPage";
        }

        books.create(book);

        return "redirect:/home";
    }

    @GetMapping("/books/{id}")
    public String showPage(Model model, @PathVariable("id") Long id, HttpSession session) {
        if(session.getAttribute("userId") == null) {
            return "redirect:/";
        }
        Book book = books.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("user", users.findById((Long)session.getAttribute("userId")));

        return "book";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model){
        Book editBook = books.details(id);
        model.addAttribute("editBook",editBook);
        return "edit";
    }
    @PutMapping("/update/{id}")
    public String update(@PathVariable Long id,@Valid @ModelAttribute("editBook") Book editBook,@NotNull BindingResult results){
        if(results.hasErrors()){
            return "edit";
        }
        books.update(editBook);
        return "redirect:/home";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        books.delete(id);
        return "redirect:/";
    }

}



