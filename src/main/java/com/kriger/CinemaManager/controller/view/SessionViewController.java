package com.kriger.CinemaManager.controller.view;

import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/sessions")
public class SessionViewController {

    private final SessionService sessionService;

    @Autowired
    public SessionViewController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    public String getSessions(Model model) {
        List<Session> sessions = sessionService.getAllSessions();
        model.addAttribute("sessions", sessions);
        return "sessions";
    }
}
