package com.Application.Restaurant.Controller;

import com.Application.Restaurant.DTO.*;
import com.Application.Restaurant.Exception.PasswordUpdateException;
import com.Application.Restaurant.Service.personService;
import com.Application.Restaurant.response.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/persons")
public class PersonController {
    private personService personService;

    //Build add employee Rest API
    @PostMapping
    public ResponseEntity<Object> SignUP(@RequestBody singUpDTO singUpDTO) {
        List<String> validationErrors = personService.checkPassword(singUpDTO.getPassword());

        if (!validationErrors.isEmpty()) {
            String errorMessage = String.join(", ", validationErrors);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, errorMessage));
        }
            return new ResponseEntity<>(personService.signUp(singUpDTO), HttpStatus.CREATED);


    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody loginDTO loginDTO) {
        return ResponseEntity.ok(personService.logIn(loginDTO));
    }

    //Build get employee Rest API
    @GetMapping("readPerson/{id}")
    public ResponseEntity<Object> readPerson(@PathVariable("id") Long iDEmployee) {
        try {
            return ResponseEntity.ok(personService.readPerson(iDEmployee));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));

        }
    }

    //Build getAll employee Rest API
    @GetMapping
    public ResponseEntity<List<PersonDTO>> reportAboutPersons() {
        List<PersonDTO> Persons = personService.getAllPersons();
        return ResponseEntity.ok(Persons);
    }

    //Build update employee Rest API
    @PutMapping("updatePerson/{id}")
    public ResponseEntity<Object> updatePerson(@PathVariable("id") Long iDEmployee, @RequestBody updatePersonDTO updatePersonDTO) {

        try {
              personService.updatePerson(iDEmployee, updatePersonDTO);
            return ResponseEntity.ok(new ApiResponse(true, "successfully updated"));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, ex.getMessage()));

        }
    }

    //Build delete employee Rest API
    @DeleteMapping("deletePerson/{id}")
    public ResponseEntity<ApiResponse> deletePerson(@PathVariable("id") Long iDEmployee) {
        try {
            personService.deletePerson(iDEmployee);
            return ResponseEntity.ok(new ApiResponse(true, "Deleting is done"));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
    }

    @PostMapping("changePassword/{idPerson}")
    public ResponseEntity<ApiResponse> changePassword(@PathVariable("idPerson") Long idPerson, @RequestBody changePasswordDTO changePasswordDTO) {
        try {
            List<String> validationErrors = personService.checkPassword(changePasswordDTO.getNewPassword());
            if (!validationErrors.isEmpty()) {
                String errorMessage = String.join(", ", validationErrors);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse(false, errorMessage));
            }
            personService.changePassword(idPerson, changePasswordDTO);
            return ResponseEntity.ok(new ApiResponse(true, "Password successfully updated."));
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
        catch (PasswordUpdateException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, ex.getMessage()));
        }
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

