package com.educa.apirest.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.educa.apirest.entities.Person;


public interface PersonRepository extends JpaRepository<Person,Long>{
	Optional<Person> findByEmail(String email);
	Optional<Person> findByCpf(String cpf);

}
