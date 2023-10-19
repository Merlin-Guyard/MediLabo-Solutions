package com.oc.medilabosolutionsfrontend.controller;

import com.oc.medilabosolutionsfrontend.service.ProxyService;
import com.oc.medilabosolutionsfrontend.service.SCHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/frontend")
public class PageController {

    private final ProxyService proxyService;

    private final SCHService schService;

    public PageController(ProxyService proxyService, SCHService schService) {
        this.proxyService = proxyService;
        this.schService = schService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        return "login";
    }

    @GetMapping("/home")
    public String home(@RequestParam(required = false) String role, Model model)
    {
        model.addAttribute("role", schService.getRole());
        model.addAttribute("name", schService.getName());

        return "home";
    }

    @RequestMapping("/getUser")
    public void getUser() {
        proxyService.getUsers("doctor");
    }
}
