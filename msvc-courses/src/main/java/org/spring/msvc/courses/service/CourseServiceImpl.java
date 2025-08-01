package org.spring.msvc.courses.service;

import org.spring.msvc.courses.clients.ClientRest;
import org.spring.msvc.courses.model.User;
import org.spring.msvc.courses.model.entity.Course;
import org.spring.msvc.courses.model.entity.CourseUser;
import org.spring.msvc.courses.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{

    private CourseRepository courseRepository;

    private ClientRest client;

    public CourseServiceImpl(CourseRepository courseRepository, ClientRest client) {
        this.courseRepository = courseRepository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }


    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            User userMsvc = client.getUser(user.getId());

            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setId(userMsvc.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);

            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }


    @Override
    @Transactional
    public Optional<User> createUser(User user, Long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            User newUserMsvc = client.createUser(user);

            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setId(newUserMsvc.getId());

            course.addCourseUser(courseUser);
            courseRepository.save(course);

            return Optional.of(newUserMsvc);
        }


        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUser(User user, Long id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            User userMsvc = client.getUser(user.getId());

            Course course = optionalCourse.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setId(user.getId());

            course.removeCourseUser(courseUser);
            courseRepository.save(course);

            return Optional.of(userMsvc);
        }


        return Optional.empty();
    }
}
