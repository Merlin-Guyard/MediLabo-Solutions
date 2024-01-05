package com.oc.medilabosolutionsreport.controller;

import com.oc.medilabosolutionsreport.service.ReportService;
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

    @RequestMapping("/getReport/{patientId}")
    public ResponseEntity<String> getReport(@PathVariable int patientId) {
        return ResponseEntity.ok(reportService.makeReport(patientId));
    }
}
