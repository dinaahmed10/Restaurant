package com.Application.Restaurant.Repository;

import com.Application.Restaurant.DTO.PersonDTO;
import com.Application.Restaurant.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface personRepository extends JpaRepository<Person,Long> {
    Optional<Person> findByEmailAndPassword(String email, String password);
    boolean existsByEmail(String email);
}
