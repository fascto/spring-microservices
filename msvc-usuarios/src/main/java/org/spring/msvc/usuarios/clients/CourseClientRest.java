package org.spring.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-courses", url = "192.168.1.14:8002")
public interface CourseClientRest {

    @DeleteMapping("/delete-user-by-id/{id}")
    void deleteUserById(@PathVariable("id") Long id);
}
