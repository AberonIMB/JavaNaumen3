package com.kriger.CinemaManager.controller.api;

import com.kriger.CinemaManager.dto.UserRequest;
import com.kriger.CinemaManager.model.User;
import com.kriger.CinemaManager.model.enums.UserRole;
import com.kriger.CinemaManager.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<UserRequest> createUser(@RequestBody UserRequest userRequest) {
        User user = userService.createUser(userRequest.getName(), userRequest.getEmail(), userRequest.getPassword(), UserRole.USER);

        return ResponseEntity.ok(new UserRequest(user.getName(), user.getEmail(), user.getPassword()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserRequest> getUserByName(@PathVariable String name) {

        User user = userService.getUserByName(name);

        return ResponseEntity.ok(new UserRequest(user.getName(), user.getEmail(), user.getPassword()));
    }
}
