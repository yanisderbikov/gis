package ru.gisbis.auth.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gisbis.auth.dto.AuthoritiesDto;


@Controller
public class SecurityController {


    @GetMapping("/user")
    @ResponseBody
    public AuthoritiesDto userPage(Authentication authentication) {

        return new AuthoritiesDto(authentication.getName());
    }

//    @GetMapping("/login")
//    public String loginPage() {
////        myService.onlyUser();
//        return "login";
//    }
}
