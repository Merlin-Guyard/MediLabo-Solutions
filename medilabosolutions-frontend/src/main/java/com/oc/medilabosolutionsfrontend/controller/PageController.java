package com.oc.medilabosolutionsfrontend.controller;

import com.oc.medilabosolutionsfrontend.Model.User;
import com.oc.medilabosolutionsfrontend.service.ProxyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/frontend")
public class PageController {

    private final ProxyService proxyService;


    public PageController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/connect")
    public String connect(@ModelAttribute User user) {
        // Afficher les données de l'objet titi dans la console
        proxyService.connect(user);

        return "redirect:/frontend/home"; // Redirige vers la page d'accueil
    }

    @GetMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("patients", proxyService.getAllPatient());
        return "home";
    }

    @RequestMapping("/getUser")
    public void getUser() {
        proxyService.getUsers("doctor");
    }
}
