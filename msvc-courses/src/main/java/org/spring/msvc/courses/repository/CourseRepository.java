package org.spring.msvc.courses.repository;

import org.spring.msvc.courses.model.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {



}
