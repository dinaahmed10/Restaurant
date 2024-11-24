package com.Application.Restaurant.Mapper;

import com.Application.Restaurant.DTO.PersonDTO;
import com.Application.Restaurant.entity.Person;

public class PersonMapper {

    public static PersonDTO mapToPersonDTO(Person Person){
       return new PersonDTO(
               Person.getId(),
               Person.getUsername(),
               Person.getPassword(),
               Person.getEmail(),
               Person.getBio()



       );
    }

    public static Person mapToPerson(PersonDTO PersonDTO){
        return new Person(
                PersonDTO.getId(),
                PersonDTO.getUsername(),
                PersonDTO.getPassword(),
                PersonDTO.getEmail(),
                PersonDTO.getBio()
        );
    }

}
