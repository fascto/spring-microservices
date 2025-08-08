package org.spring.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.spring.msvc.usuarios.models.entity.User;
import org.spring.msvc.usuarios.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long id) {
        Optional<User> user = userService.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }

        if (!user.getEmail().isEmpty() && userService.existsUserByEmail(user.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "An user already registered with that email"));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody User user, BindingResult result, @PathVariable("id") Long id) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {
            User userDb = userOptional.get();
            if (
                    !user.getEmail().isEmpty() &&
                            !user.getEmail().equalsIgnoreCase(userDb.getEmail()) &&
                            userService.findByEmail(user.getEmail()).isPresent()
            ) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "An user already registered with that email"));
            }
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());

            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userDb));

        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        Optional<User> userOptional = userService.findById(id);

        if (userOptional.isPresent()) {
            userService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/course-users")
    public ResponseEntity<?> getCourseUsers(@RequestParam("idList") List<Long> idList) {
        return ResponseEntity.ok(userService.findAllById(idList));
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}