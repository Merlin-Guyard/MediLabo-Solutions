//package com.oc.medilabosolutionsfrontend.controller;
//
//import com.oc.medilabosolutionsfrontend.model.NotesFetchException;
//import org.pmw.tinylog.Logger;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class CustomExceptionHandler {
//
//    @ExceptionHandler(NotesFetchException.class)
//    public String handleNotesFetchException(NotesFetchException ex, Model model) {
//        Logger.error("Error fetching notes", ex);
//        model.addAttribute("notesFetchError", ex.getErrorMessage());
//        return "view";
//    }
//}
