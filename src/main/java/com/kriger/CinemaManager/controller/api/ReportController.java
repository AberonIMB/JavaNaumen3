package com.kriger.CinemaManager.controller.api;

import com.kriger.CinemaManager.dto.ReportResponse;
import com.kriger.CinemaManager.model.Report;
import com.kriger.CinemaManager.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createReport() {
        Long id = reportService.createReport();
        reportService.generateReport(id);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportResponse> getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);

        return ResponseEntity.ok(new ReportResponse(id, report.getStatus(), report.getContent()));
    }

    @GetMapping(value = "/{id}/html", produces = MediaType.TEXT_HTML_VALUE)
    public String getReportHtml(@PathVariable Long id) {
        Report report = reportService.getReport(id);

        return report.getContent();
    }
}