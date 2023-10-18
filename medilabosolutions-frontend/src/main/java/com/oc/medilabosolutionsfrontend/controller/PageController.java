package com.oc.medilabosolutionsfrontend.controller;

import com.oc.medilabosolutionsfrontend.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class PageController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
