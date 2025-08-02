package org.spring.msvc.courses.clients;

import org.spring.msvc.courses.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-users", url="localhost:8001")
public interface ClientRest {
        @GetMapping("/{id}")
        User getUser(@PathVariable("id") Long id);

        @PostMapping("")
        User createUser(@RequestBody User user);

        @GetMapping("/course-users")
        Iterable<User> getCourseUsers(@RequestParam("idList") List<Long> idList);

}
