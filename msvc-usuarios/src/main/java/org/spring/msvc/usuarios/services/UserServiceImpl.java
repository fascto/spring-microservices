// ===============================================
// UserService.java - INTERFACE CORREGIDA
// ===============================================
package org.spring.msvc.usuarios.services;

import org.spring.msvc.usuarios.models.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import org.spring.msvc.usuarios.clients.CourseClientRest;
import org.spring.msvc.usuarios.models.entity.User;
import org.spring.msvc.usuarios.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseClientRest courseClientRest;

    public UserServiceImpl(UserRepository userRepository, CourseClientRest courseClientRest) {
        this.userRepository = userRepository;
        this.courseClientRest = courseClientRest;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // PRIMERO: Eliminar el usuario de todos los cursos
        try {
            courseClientRest.deleteUserById(id);
        } catch (Exception e) {
            // Log el error pero continúa con la eliminación local
            System.err.println("Error al eliminar usuario de cursos: " + e.getMessage());
        }

        // SEGUNDO: Eliminar el usuario de la base de datos local
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllById(Iterable<Long> idList) {
        return (List<User>) userRepository.findAllById(idList);
    }
}