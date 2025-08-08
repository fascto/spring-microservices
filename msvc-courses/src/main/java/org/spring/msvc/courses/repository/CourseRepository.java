package org.spring.msvc.courses.repository;

import org.spring.msvc.courses.model.entity.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CourseRepository extends CrudRepository<Course, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CourseUser cu WHERE cu.userId=?1")
    void deleteUserById(Long id);
}