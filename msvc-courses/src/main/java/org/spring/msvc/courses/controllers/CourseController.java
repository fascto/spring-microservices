package org.spring.msvc.courses.controllers;

import feign.FeignException;
import jakarta.servlet.ServletResponse;
import jakarta.validation.Valid;
import org.spring.msvc.courses.model.User;
import org.spring.msvc.courses.model.entity.Course;
import org.spring.msvc.courses.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    private final CourseService courseService;
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getCourses() {
        List<Course> courseList = courseService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(courseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        Optional<Course> courseOptional = courseService.findByIdDetailed(id);
        if (courseOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body((courseOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> createCourse(@Valid @RequestBody Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return validate(bindingResult);
        }

        Course courseDb = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@Valid @RequestBody Course course, BindingResult bindingResult, @PathVariable Long id, ServletResponse servletResponse) {
        if (bindingResult.hasErrors()) {
            return validate(bindingResult);
        }
        Optional<Course> courseOptional = courseService.findById(id);
        if (courseOptional.isPresent()) {
            Course courseDb = courseOptional.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id) {
        Optional<Course> courseOptional = courseService.findById(id);
        if (courseOptional.isPresent()) {
            courseService.delete(courseOptional.get().getId());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable("courseId") Long courseId) {
        Optional<User> optionalUser = Optional.empty();
        try  {
            optionalUser = courseService.assignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("message", "The user doesnt exist or a internal error occurred: " +
                            e.getMessage() ));
        }
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }
        return  ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user, @PathVariable("courseId") Long courseId) {
        Optional<User> optionalUser = Optional.empty();
        try  { optionalUser = courseService.createUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("message", "Internal server error: " +
                            e.getMessage() ));
        }
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }
        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{courseId}")
    public ResponseEntity<?> deleteUser(@RequestBody User user, @PathVariable Long courseId) {
        Optional<User> optionalUser = Optional.empty();
        try  {
            optionalUser = courseService.removeUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections
                    .singletonMap("message", "Internal server error: " +
                            e.getMessage() ));
        }
        if (optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalUser.get());
        }
        return  ResponseEntity.notFound().build();
    }
    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}

