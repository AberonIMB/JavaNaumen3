package com.kriger.CinemaManager.dto;

import com.kriger.CinemaManager.model.enums.ReportStatus;

public class ReportResponse {

    private Long id;
    private ReportStatus status;
    private String content;

    public ReportResponse(Long id, ReportStatus status, String content) {
        this.id = id;
        this.status = status;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
