package com.Application.Restaurant.Service;

import com.Application.Restaurant.DTO.*;

import java.util.List;

public interface personService {
    List<Object> signUp(singUpDTO singUpDTO);
    List<Object>  logIn(loginDTO loginDTO);
    PersonDTO readPerson(Long id);
    List<PersonDTO> getAllPersons();
    void updatePerson(Long id , updatePersonDTO updatePersonDTO);
    void deletePerson(Long id);
    void changePassword(Long idPerson,changePasswordDTO changePasswordDTO);
    public List<String>  checkPassword(String password);
}
