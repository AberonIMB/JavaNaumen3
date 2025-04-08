package com.kriger.CinemaManager.controller;

import com.kriger.CinemaManager.dto.SessionRequest;
import com.kriger.CinemaManager.dto.SessionResponse;
import com.kriger.CinemaManager.model.Session;
import com.kriger.CinemaManager.service.interfaces.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/sessions")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/filterTimeAndHall")
    public ResponseEntity<List<SessionResponse>> getActiveSessionsByTimeAndHall(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end,
            @RequestParam("hallId") Long hallId) {

        List<SessionResponse> sessions = sessionService.getActiveSessionsByTimeAndHall(hallId, start, end)
                .stream().map(SessionResponse::new).toList();

        return ResponseEntity.ok(sessions);
    }

    @GetMapping("/filterMovie")
    public ResponseEntity<List<SessionResponse>> getSessionsByMovieTitle(
            @RequestParam("title") String title) {

        List<SessionResponse> sessions = sessionService.getSessionsByMovieTitle(title)
                .stream().map(SessionResponse::new).toList();

        return ResponseEntity.ok(sessions);
    }

    @PostMapping("/create")
    public ResponseEntity<SessionResponse> createSession(@RequestBody SessionRequest sessionRequest) {

        Session session = sessionService.createSession(
                sessionRequest.getStartTime(),
                sessionRequest.getHallId(),
                sessionRequest.getMovieId());

        SessionResponse sessionResponse = new SessionResponse(session);

        return ResponseEntity.ok(sessionResponse);
    }
}