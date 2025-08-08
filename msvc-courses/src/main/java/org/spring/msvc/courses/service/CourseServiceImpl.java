package org.spring.msvc.courses.service;

import org.spring.msvc.courses.clients.ClientRest;
import org.spring.msvc.courses.model.User;
import org.spring.msvc.courses.model.entity.Course;
import org.spring.msvc.courses.model.entity.CourseUser;
import org.spring.msvc.courses.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Optional<Course> findByIdDetailed(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);

        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            if (!course.getCourseUsers().isEmpty()) {
                List<Long> idList = course.getCourseUsers().stream().map(courseUser -> courseUser.getUserId())
                        .collect(Collectors.toList());

                List<User> users = (List<User>) client.getCourseUsers(idList);

                course.setUsers(users);
                return Optional.of(course);
            }
        }
        return Optional.empty();
    }

    @Override
    public void deleteUserById(Long id) {
        courseRepository.deleteUserById(id);
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
            courseUser.setUserId(userMsvc.getId());

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
            courseUser.setUserId(newUserMsvc.getId());

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
            courseUser.setUserId(user.getId());

            course.removeCourseUser(courseUser);
            courseRepository.save(course);

            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }

}
