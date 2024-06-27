package me.dio.domain.controller;

import me.dio.domain.model.User;
import me.dio.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable long id) {
        var userGot = userService.findById(id);
        return ResponseEntity.ok(userGot);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        var userCreated = userService.create(user);
        // return ResponseEntity.ok(userCreated);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(userCreated);
    }

}
