package com.Application.Restaurant.Controller;

import com.Application.Restaurant.DTO.PersonDTO;
import com.Application.Restaurant.DTO.loginDTO;
import com.Application.Restaurant.DTO.singUpDTO;
import com.Application.Restaurant.Service.personService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {
    private personService personService;

    //Build add employee Rest API
    @PostMapping
    public ResponseEntity<Object> SignUP(@RequestBody singUpDTO singUpDTO){
        return new  ResponseEntity<>(personService.signUp(singUpDTO), HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody loginDTO loginDTO) {
        return  ResponseEntity.ok(personService.logIn(loginDTO));
    }

    //Build get employee Rest API
    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonByID(@PathVariable("id") Long iDEmployee){
        return ResponseEntity.ok( personService.getPersonByID(iDEmployee));
    }

    //Build getAll employee Rest API
    @GetMapping
    public ResponseEntity<List<PersonDTO>> reportAboutPersons(){
        List<PersonDTO> Persons= personService.getAllPersons();
        return ResponseEntity.ok(Persons);
    }

    //Build update employee Rest API
    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePerson(@PathVariable("id") Long iDEmployee,@RequestBody PersonDTO PersonDTO){
        PersonDTO savedPersonDTO= personService.updatePerson(iDEmployee,PersonDTO);
        return ResponseEntity.ok(savedPersonDTO);
    }

    //Build delete employee Rest API
    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deletePerson(@PathVariable("id") Long iDEmployee){
           personService.deletePerson(iDEmployee);
        return ResponseEntity.ok("Deleting is done");
    }

}

/*
        if (personService.logIn(loginDTO).isPresent()) {

            PersonDTO personDTO=personService.logIn(loginDTO).get();
            if(loginDTO.getPassword().equals(personDTO.getPassword())){
                return ResponseEntity.ok("Login successful");
            }else {
                return ResponseEntity.ok("Password is not successful");

            }

        } else {
            return ResponseEntity.status(401).body("Login failed");
        }
    }
    */




/*
    @GetMapping("/login")
    public ResponseEntity<String>  login (@RequestBody loginDTO loginDTO) {
            if (personService.logIn(loginDTO) != null) {
                return ResponseEntity.ok("logIn is done");
            } else {
                return ResponseEntity.ok("Not Match");
            }

        }
    }*/

