package com.oc.medilabosolutionsfrontend.controller;

import com.oc.medilabosolutionsfrontend.Model.Patient;
import com.oc.medilabosolutionsfrontend.Model.User;
import com.oc.medilabosolutionsfrontend.service.ProxyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/frontend")
public class PageController {

    private final ProxyService proxyService;


    public PageController(ProxyService proxyService) {
        this.proxyService = proxyService;
    }

    //
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    //
    @PostMapping("/connect")
    public String connect(@ModelAttribute User user) {

        if (proxyService.login(user)) {
            return "redirect:/frontend/home"; // Redirige vers la page d'accueil
        }
        return "redirect:/frontend/login";
    }

    //
    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("patients", proxyService.getAllPatient());

        if (proxyService.verify()) {
            return "home";
        }
        return "redirect:/frontend/login";
    }

    @GetMapping("/delete/{id}")
    public String deletePatientPage(@PathVariable("id") Integer id, Model model) {

        if (proxyService.verify()) {
            proxyService.deleteById(id);
            return "redirect:/frontend/home";
        }
        return "redirect:/frontend/login";
    }

    @GetMapping("/view/{id}")
    public String viewPatientPage(@PathVariable("id") Integer id, Model model) {

        if (proxyService.verify()) {
            model.addAttribute("patient", proxyService.getPatient(id));
            return "view";
        }
        return "redirect:/frontend/login";
    }

    @GetMapping("/update/{id}")
    public String updatePatientPage(@PathVariable("id") Integer id, Model model) {

        if (proxyService.verify()) {
            model.addAttribute("patient", proxyService.getPatient(id));
            return "update";
        }
        return "redirect:/frontend/login";
    }

    @PostMapping("/update/{id}")
    public String updatePatient(@PathVariable("id") Integer id, Patient patient, Model model) {

        if (proxyService.verify()) {
            proxyService.updatePatient(id, patient);
            return "redirect:/frontend/home";
        }
        return "redirect:/frontend/login";
    }

    //
    @GetMapping("/addPatient")
    public String showAddPatient(Patient patient) {

        if (proxyService.verify()) {
            return "add";
        }
        return "redirect:/frontend/login";

    }

    //
    @PostMapping("/addPatient")
    public String addPatient(@Valid Patient patient, BindingResult bindingResult, Model model) {

        if (proxyService.verify()) {
            if(bindingResult.hasErrors()){
                return "add";
            }

            proxyService.addPatient(patient);
            return "redirect:/frontend/home";
        }
        return "redirect:/frontend/login";
    }

    //
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {
        proxyService.deleteAll();
        return new ResponseEntity<>("All patients deleted", HttpStatus.OK);
    }

}