package org.spring.msvc.usuarios.repository;

import org.spring.msvc.usuarios.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
