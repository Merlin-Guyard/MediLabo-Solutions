package com.oc.medilabosolutionsreport.controller;

import com.oc.medilabosolutionsreport.service.ReportService;
import org.pmw.tinylog.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    //Check if application is on
    @RequestMapping("/")
    public ResponseEntity<String> healthCheck() {

        Logger.info("Checking if Application is online");

        return ResponseEntity.ok("Application online !");
    }

    //Generate a report request
    @RequestMapping("/getReport/{patientId}")
    public ResponseEntity<String> getReport(@PathVariable int patientId) {

        Logger.info("Generating a report");

        return ResponseEntity.ok(reportService.makeReport(patientId));
    }
}
