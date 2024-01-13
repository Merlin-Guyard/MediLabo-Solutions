package com.oc.medilabosolutionsfrontend.controller;

import com.oc.medilabosolutionsfrontend.Exceptions.MicroserviceDownException;
import com.oc.medilabosolutionsfrontend.model.Note;
import com.oc.medilabosolutionsfrontend.model.Patient;
import com.oc.medilabosolutionsfrontend.model.User;
import com.oc.medilabosolutionsfrontend.service.GatewayService;
import com.oc.medilabosolutionsfrontend.service.NoteService;
import com.oc.medilabosolutionsfrontend.service.PatientService;
import com.oc.medilabosolutionsfrontend.service.ReportService;
import jakarta.validation.Valid;
import org.pmw.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/frontend")
public class PageController {

    private final PatientService patientService;

    private final NoteService noteService;

    private final ReportService reportService;

    private final GatewayService gatewayService;


    public PageController(PatientService patientService, NoteService noteService, ReportService reportService, GatewayService gatewayService) {
        this.patientService = patientService;
        this.noteService = noteService;
        this.reportService = reportService;
        this.gatewayService = gatewayService;
    }

    //Show login page
    @GetMapping("/login")
    public String loginPage(Model model) {

        model.addAttribute("user", new User());

        return "login";
    }

    //Send login request
    @PostMapping("/connect")
    public String connect(@ModelAttribute User user) {

        if (!gatewayService.login(user)) {
            return "redirect:/frontend/login";
        }

        return "redirect:/frontend/home";
    }

    //Show home page
    @GetMapping("/home")
    public String homePage(Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        List<Patient> patients = patientService.getAllPatient();
        model.addAttribute("patients", patients);

        return "home";
    }

    //Show view page
    @GetMapping("/view/{id}")
    public String viewPatientPage(@PathVariable("id") Integer id, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        Patient patient = patientService.getPatient(id);
        model.addAttribute("patient", patient);

        try {
            List<Note> notes = noteService.getNotes(patient.getId());
            model.addAttribute("notes", notes);
        } catch (MicroserviceDownException e) {
            Logger.error("Error fetching notes", e);
            model.addAttribute("noteFetchError", e.getErrorMessage());
            model.addAttribute("notes", Collections.emptyList());
        }

        try {
            String report = reportService.getReport(patient.getId());
            model.addAttribute("report", report);
        } catch (MicroserviceDownException e) {
            Logger.error("Error fetching Report", e);
            model.addAttribute("reportFetchError", e.getErrorMessage());
            model.addAttribute("report", "");
        }

        return "view";
    }

    //Send delete patient by id request
    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable("id") Integer id, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        patientService.deletePatientById(id);

        return "redirect:/frontend/home";
    }

    //Show patient page
    @GetMapping("/addPatient")
    public String showAddPatient(Patient patient) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";

        }

        return "add";
    }

    //Send Patient addition request
    @PostMapping("/addPatient")
    public String addPatient(@Valid Patient patient, BindingResult bindingResult, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        if (bindingResult.hasErrors()) {
            return "add";
        }

        patientService.addPatient(patient);

        return "redirect:/frontend/home";

    }

    //Send delete note by id request
    @GetMapping("/deleteNote/{id}")
    public String deleteNote(@PathVariable("id") String id, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        noteService.deleteNoteById(id);

        return "redirect:/frontend/home";
    }

    //Show Update page
    @GetMapping("/updatePatient/{id}")
    public String updatePatientPage(@PathVariable("id") Integer id, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        Patient patient = patientService.getPatient(id);
        model.addAttribute("patient", patient);

        return "updatePatient";
    }

    //Send update patient with id request
    @PostMapping("/updatePatient/{id}")
    public String updatePatient(@PathVariable("id") Integer id, Patient patient, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        patientService.updatePatient(id, patient);

        return "redirect:/frontend/home";
    }

    //Show note page
    @GetMapping("/addNotes/{id}")
    public String addNotesPage(@PathVariable("id") Integer id, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";
        }

        Patient patient = patientService.getPatient(id);
        model.addAttribute("patient", patient);

        List<Note> notes = noteService.getNotes(patient.getId());
        model.addAttribute("notes", notes);

        model.addAttribute("note", new Note());

        return "addNotes";
    }

    //Send note addition request
    @PostMapping("/addNotes/{patientId}")
    public String addNotes(@PathVariable("patientId") Integer patientId, @ModelAttribute("note") Note note, Model model) {

        if (!gatewayService.verify()) {
            return "redirect:/frontend/login";

        }

        note.setPatientId(String.valueOf(patientId));
        noteService.addNotes(note);

        return "redirect:/frontend/view/" + patientId;
    }


    //Send Delete request (testing with Postman only)
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll() {

        patientService.deleteAll();

        return new ResponseEntity<>("All patients deleted", HttpStatus.OK);
    }

}