package com.kriger.CinemaManager.controller.api;

import com.kriger.CinemaManager.dto.HallDTO;
import com.kriger.CinemaManager.model.Hall;
import com.kriger.CinemaManager.service.interfaces.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/halls")
public class HallController {

    private final HallService hallService;

    @Autowired
    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @PostMapping("create")
    public ResponseEntity<Hall> createHall(@RequestBody HallDTO hallRequest) {
        Hall hall = hallService.createHall(hallRequest.getName(), hallRequest.getRows(), hallRequest.getSeatsInRow());
        return ResponseEntity.ok(hall);
    }
}
