package org.spring.msvc.usuarios.services;

import org.spring.msvc.usuarios.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void delete(Long id);

    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    List<User> findAllById(Iterable<Long> idList);
}