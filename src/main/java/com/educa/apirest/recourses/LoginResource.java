package com.educa.apirest.recourses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.educa.apirest.entities.Person;
import com.educa.apirest.service.PersonService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/login")
public class LoginResource {
	@Autowired
	private PersonService personService;
	public LoginResource() {};
    
	@PostMapping
	public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
		Person person = personService.findByEmail(loginRequest.getEmail());
		if (person != null && person.getSenha().equals(loginRequest.getSenha())) {
            return ResponseEntity.ok("Login bem-sucedido");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha no login");
        }
		
}
}
