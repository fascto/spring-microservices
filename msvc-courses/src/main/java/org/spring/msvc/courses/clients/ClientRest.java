package org.spring.msvc.courses.clients;

import org.spring.msvc.courses.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="msvc-users", url="localhost:8001")
public interface ClientRest {
        @GetMapping("/{id}")
        User getUser(@PathVariable Long id);

        @GetMapping("/")
        User createUser(@RequestBody User user);
}
