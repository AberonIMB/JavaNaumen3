package com.kriger.CinemaManager.service.impl;

import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.model.Report;
import com.kriger.CinemaManager.model.enums.ReportStatus;
import com.kriger.CinemaManager.repository.HallRepository;
import com.kriger.CinemaManager.repository.ReportRepository;
import com.kriger.CinemaManager.repository.UserRepository;
import com.kriger.CinemaManager.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final HallRepository hallRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository,
                             UserRepository userRepository,
                             HallRepository hallRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        reportRepository.save(report);
        return report.getId();
    }

    @Override
    public void generateReport(Long id) {
        Report report = reportRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Report not found"));

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            long startMethodTime = System.currentTimeMillis();
            AtomicLong elapsedUsersCountTime = new AtomicLong();
            AtomicLong elapsedFindAllHallsTime = new AtomicLong();

            AtomicLong usersCount = new AtomicLong();
            List<Hall> halls = new ArrayList<>();

            try {
                CompletableFuture<Void> usersFuture = CompletableFuture.runAsync(() -> {
                    long startUsersCountTime = System.currentTimeMillis();
                    usersCount.set(userRepository.count());
                    elapsedUsersCountTime.set(System.currentTimeMillis() - startUsersCountTime);
                });

                CompletableFuture<Void> hallsFuture = CompletableFuture.runAsync(() -> {
                    long startHallsCountTime = System.currentTimeMillis();
//                    int s = 2 / 0; // Исключение здесь
                    hallRepository.findAll().forEach(halls::add);
                    elapsedFindAllHallsTime.set(System.currentTimeMillis() - startHallsCountTime);
                });

                CompletableFuture.allOf(usersFuture, hallsFuture).join();

                Thread.sleep(15000);
                long elapsedMethodTime = System.currentTimeMillis() - startMethodTime;

                String content = generateReportContent(usersCount.get(),
                        halls, elapsedUsersCountTime.get(), elapsedFindAllHallsTime.get(), elapsedMethodTime, id);

                report.setContent(content);
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            } catch (Exception e) {
                report.setStatus(ReportStatus.FAILED);
                report.setContent("Произошла ошибка во время формирования отчета: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }

//    @Override
//    public void generateReport(Long id) {
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            Report report = reportRepository.findById(id).orElseThrow(
//                    () -> new IllegalArgumentException("Report not found"));
//
//            long startMethodTime = System.currentTimeMillis();
//            AtomicLong elapsedUsersCountTime = new AtomicLong();
//            AtomicLong elapsedFindAllHallsTime = new AtomicLong();
//
//            AtomicLong usersCount = new AtomicLong();
//            List<Hall> halls = new ArrayList<>();
//
//            AtomicReference<Exception> exception = new AtomicReference<>();
//
//            Thread getUsersCount = new Thread(() -> {
//                try {
//                    long startUsersCountTime = System.currentTimeMillis();
//                    usersCount.set(userRepository.count());
//                    elapsedUsersCountTime.set(System.currentTimeMillis() - startUsersCountTime);
//                } catch (Exception e) {
//                    exception.set(e);
//                }
//            });
//
//            Thread getHalls = new Thread(() -> {
//                try {
//                    long startHallsCountTime = System.currentTimeMillis();
//                    int s = 2 / 0;
//                    hallRepository.findAll().forEach(halls::add);
//                    elapsedFindAllHallsTime.set(System.currentTimeMillis() - startHallsCountTime);
//                } catch (Exception e) {
//                    exception.set(e);
//                }
//            });
//
//            getUsersCount.start();
//            getHalls.start();
//
//            try {
//                getUsersCount.join();
//                getHalls.join();
//
//                if (exception.get() != null) {
//                    throw exception.get();
//                }
//
//                long elapsedMethodTime = System.currentTimeMillis() - startMethodTime;
//
//                String content = generateReportContent(usersCount.get(),
//                        halls, elapsedUsersCountTime.get(), elapsedFindAllHallsTime.get(), elapsedMethodTime, id);
//
//                report.setContent(content);
//                report.setStatus(ReportStatus.COMPLETED);
//                reportRepository.save(report);
//
//            } catch (Exception e) {
//                report.setStatus(ReportStatus.FAILED);
//                report.setContent("Произошла ошибка во время формирования отчета: " + e.getMessage());
//                reportRepository.save(report);
//            }
//        });
//    }

    @Override
    public Report getReport(Long id) {

        return reportRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Report not found")
        );
    }

    @Override
    public List<Report> getAllReports() {
        List<Report> reports = new ArrayList<>();

        reportRepository.findAll().forEach(reports::add);

        return reports;
    }

    private String generateReportContent(long usersCount,
                                         List<Hall> halls,
                                         long elapsedUsersCountTime,
                                         long elapsedFindAllHallsTime,
                                         long elapsedMethodTime,
                                         Long reportId) {

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>")
                .append("<html>")
                .append("<head>")
                .append("<title>Отчёт</title>")
                .append("<style>")
                .append("body { font-family: Arial, sans-serif; margin: 20px; background-color: #f9f9f9; }")
                .append("h1 { color: #333; }")
                .append("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }")
                .append("th, td { border: 1px solid #ccc; padding: 10px; text-align: left; }")
                .append("th { background-color: #f2f2f2; }")
                .append("tr:nth-child(even) { background-color: #f9f9f9; }")
                .append("ul { padding-left: 20px; }")
                .append("li { margin-bottom: 5px; }")
                .append("</style>")
                .append("</head>")
                .append("<body>");

        sb.append("<h1>Отчет №").append(reportId).append("</h1>");
        sb.append("<table>");
        sb.append("<tr><th>Метрика</th><th>Значение</th><th>Время (мс)</th></tr>");

        sb.append("<tr><td>Количество пользователей</td><td>")
                .append(usersCount)
                .append("</td><td>")
                .append(elapsedUsersCountTime)
                .append("</td></tr>");

        sb.append("<tr><td>Количество залов</td><td>");

        sb.append("<ul>");
        halls.forEach(hall -> sb.append("<li>").append(hall.getName()).append("</li>"));
        sb.append("</ul>");

        sb.append("</td><td>")
                .append(elapsedFindAllHallsTime)
                .append("</td></tr>");

        sb.append("</table>");

        sb.append("<p><strong>Общее время формирования отчета:</strong> ")
                .append(elapsedMethodTime)
                .append(" мс</p>");

        sb.append("</body></html>");
        return sb.toString();
    }
}
