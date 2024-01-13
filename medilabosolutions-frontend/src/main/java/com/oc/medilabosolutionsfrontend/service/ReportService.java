package com.oc.medilabosolutionsfrontend.service;

import com.oc.medilabosolutionsfrontend.proxy.ReportProxy;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportProxy reportProxy;

    public ReportService(ReportProxy reportProxy) {
        this.reportProxy = reportProxy;
    }

    public String getReport(int id) {
        return reportProxy.getReport(id);
    }
}
