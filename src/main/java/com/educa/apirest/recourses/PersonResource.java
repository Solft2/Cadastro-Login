package com.educa.apirest.recourses;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.educa.apirest.entities.Person;
import com.educa.apirest.service.PersonService;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/cadastro")
public class PersonResource {
	@Autowired
    private PersonService personService;
	public PersonResource() {}
	@GetMapping
    public ResponseEntity<List<Person>> findAll(){
    	List<Person> list = personService.findAll();
    	return ResponseEntity.status(200).body(list);
    }
	@GetMapping(value = "/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id){
    	Person obj = personService.findById(id);
    	return ResponseEntity.ok().body(obj);
    }
	
	@PostMapping
	public ResponseEntity<Person> personInsert(@RequestBody Person obj){
		
		obj = personService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		personService.delete(id);
		return ResponseEntity.noContent().build();
	}
	@PutMapping(value = "/{id}")
	public ResponseEntity<Person> update(@PathVariable Long id,@RequestBody Person obj){
		obj = personService.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
}
