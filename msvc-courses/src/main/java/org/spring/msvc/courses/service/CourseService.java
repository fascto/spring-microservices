package org.spring.msvc.courses.service;

import org.spring.msvc.courses.model.User;
import org.spring.msvc.courses.model.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    List<Course> findAll();


    Optional<Course> findById(Long id);

    Optional <Course> findByIdDetailed(Long id);

    void deleteUserById(Long id);

    Course save(Course course);

    void delete(Long id);

    // Metodos para comunicar con el otro servicio mediante una API REST.
    Optional<User> assignUser(User user, Long id);

    Optional<User> createUser(User user, Long id);

    Optional<User> removeUser(User user, Long id);

}
