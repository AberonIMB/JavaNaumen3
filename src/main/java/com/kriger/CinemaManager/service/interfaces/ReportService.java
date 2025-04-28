package com.kriger.CinemaManager.service.interfaces;

import com.kriger.CinemaManager.model.Report;

import java.util.List;

public interface ReportService {

    Long createReport();
    void generateReport(Long id);
    Report getReport(Long id);
    List<Report> getAllReports();
}