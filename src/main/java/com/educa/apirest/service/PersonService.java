package com.educa.apirest.service;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.educa.apirest.entities.Person;
import com.educa.apirest.repositories.PersonRepository;
@Service
public class PersonService {
    @Autowired
	private PersonRepository personRepository; 
	
	public PersonService() {}
	
	public List<Person> findAll(){
		return personRepository.findAll();
	}
   	
   public Person findById(Long id) {
	   Optional<Person> obj = personRepository.findById(id);
		   return obj.get();   
   }
   
   public Person findByEmail(String email) {
       Optional<Person> optionalPerson = personRepository.findByEmail(email);
       return optionalPerson.orElse(null);
   }
   
   public Person insert(Person obj) {
	  
       if (cpfValido(obj.getCpf())) {          
           if (!isEmailAndCpfAlreadyInUse(obj.getEmail(), obj.getCpf())) {
               return personRepository.save(obj);
           } else {
               throw new RuntimeException("Email e/ou CPF já estão em uso.");
           }
       } else {
           throw new RuntimeException("CPF inválido.");
       }
   }
   public void delete(Long id) {
	   personRepository.deleteById(id);
   }
   public Person update(Long id, Person obj) {
	   Person entity = personRepository.getReferenceById(id);
	   updateData(entity,obj);
	   return personRepository.save(entity);
   }

	private void updateData(Person entity, Person obj) {
	entity.setEmail(obj.getEmail());
	entity.setName(obj.getName());
	entity.setTelefone(obj.getTelefone());
	entity.setCpf(obj.getCpf());
	entity.setSenha(obj.getSenha());
			
	}
		private boolean cpfValido(String cpf) {
		    cpf = cpf.replaceAll("[^0-9]", ""); 
		    
		    if (cpf.equals("00000000000") ||
		        cpf.equals("11111111111") ||
		        cpf.equals("22222222222") || cpf.equals("33333333333") ||
		        cpf.equals("44444444444") || cpf.equals("55555555555") ||
		        cpf.equals("66666666666") || cpf.equals("77777777777") ||
		        cpf.equals("88888888888") || cpf.equals("99999999999") ||
		        (cpf.length() != 11)) { 
		        System.out.println("CPF inválido: " + cpf); 
		        return false;
		    }

		    char dig10, dig11;
		    int sm, i, r, num, peso;

		    try {
		        sm = 0;
		        peso = 10;
		        for (i = 0; i < 9; i++) {
		            num = (int) (cpf.charAt(i) - '0'); 
		            sm = sm + (num * peso);
		            peso = peso - 1;
		        }

		        r = 11 - (sm % 11);
		        if (r == 10 || r == 11) {
		            dig10 = '0';
		        } else {
		            dig10 = (char) (r + '0'); 
		        }

		        sm = 0;
		        peso = 11;
		        for (i = 0; i < 10; i++) {
		            num = (int) (cpf.charAt(i) - '0');
		            sm = sm + (num * peso);
		            peso = peso - 1;
		        }

		        r = 11 - (sm % 11);
		        if (r == 10 || r == 11) {
		            dig11 = '0';
		        } else {
		            dig11 = (char) (r + '0'); 
		        }

		        if (dig10 == cpf.charAt(9) && dig11 == cpf.charAt(10)) {
		            return true;
		        } else {
		            System.out.println("CPF inválido após validação: " + cpf);
		            return false;
		        }
		    } catch (InputMismatchException erro) {
		        System.out.println("Erro durante a validação do CPF: " + cpf); 
		        return false;
		    }
	        }
	  private boolean isEmailAndCpfAlreadyInUse(String email, String cpf) {
        return personRepository.findByEmail(email).isPresent() || personRepository.findByCpf(cpf).isPresent();
    }

	}
   
