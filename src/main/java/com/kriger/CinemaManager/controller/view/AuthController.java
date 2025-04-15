package com.kriger.CinemaManager.controller.view;

import com.kriger.CinemaManager.dto.UserRegisterDTO;
import com.kriger.CinemaManager.exception.EmailAlreadyExistsException;
import com.kriger.CinemaManager.exception.UsernameAlreadyExistsException;
import com.kriger.CinemaManager.model.enums.UserRole;
import com.kriger.CinemaManager.service.interfaces.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new UserRegisterDTO());
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("user") @Valid UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult,
                           @RequestParam(required = false) UserRole userRole) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            userService.registerUser(userRegisterDTO, userRole);
            return "redirect:/login";
        } catch (UsernameAlreadyExistsException e) {
            bindingResult.rejectValue("username", "username.exists", e.getMessage());
        } catch (EmailAlreadyExistsException e) {
            bindingResult.rejectValue("email", "email.exists", e.getMessage());
        }

        return "registration";

//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//
//        return "redirect:/login";
    }

}