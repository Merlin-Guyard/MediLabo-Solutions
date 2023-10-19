package com.oc.medilabosolutionsfrontend.controller;

import com.oc.medilabosolutionsfrontend.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/frontend")
public class PageController {

    @Autowired
    private final ProxyService proxyService;

    public PageController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        return "home";
    }

    @RequestMapping("/getUser")
    public void getUser() {
        proxyService.getUsers("doctor");
    }
}
