package com.Application.Restaurant.Service.Impl;

import com.Application.Restaurant.DTO.PersonDTO;
import com.Application.Restaurant.DTO.loginDTO;
import com.Application.Restaurant.DTO.singUpDTO;
import com.Application.Restaurant.entity.Person;
import com.Application.Restaurant.Mapper.PersonMapper;
import com.Application.Restaurant.Repository.personRepository;
import com.Application.Restaurant.Service.personService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements personService {
    private personRepository personRepository;

    @Override
    public List<Object> signUp(singUpDTO singUpDTO) {
        List<Object> myList = new ArrayList<>();
        Person Person = new Person();

        ////////////////////////////  Username   /////////////////////////////
        if (singUpDTO.getUsername().isEmpty()) {
            myList.add("Username cannot by empty");
        } else {

            if ((singUpDTO.getUsername().matches(".*[0-9].*"))) {
                myList.add("Username must not contain numbers");
            }
            if ((singUpDTO.getUsername().matches(".*[@$#^()+=_.!%*?&].*"))) {
                    myList.add("Username must not contain special Charter");
                }
            if(singUpDTO.getUsername().length() < 3 ||  singUpDTO.getUsername().length() > 15  ){
                myList.add("Username length must be between 3 and 15 characters in length");
            }


            if (singUpDTO.getUsername().length() >= 3 && singUpDTO.getUsername().length() <= 15
                    && !singUpDTO.getUsername().matches(".*[0-9].*")
                    && !singUpDTO.getUsername().matches(".*[@$#^()_.!%*?&].*"))
                   {

                        Person.setUsername(singUpDTO.getUsername());
                         myList.add("Username is Valid");
                    }


                }


            //////////////////////////////

            ///////////////////////////  Password  /////////////////////////
            if (singUpDTO.getPassword().isEmpty()) {
                myList.add("Password cannot by empty");
            }
            else {
                if ( singUpDTO.getPassword().length() >= 8 && singUpDTO.getPassword().length() <= 20
                        && singUpDTO.getPassword().matches(".*[A-Z].*")
                        && singUpDTO.getPassword().matches(".*[a-z].*")
                        && singUpDTO.getPassword().matches(".*[0-9].*")
                        && singUpDTO.getPassword().matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\\\"\\|,.<>/?].*")

                )
                {
                    Person.setPassword(singUpDTO.getPassword());
                    myList.add("Password is Valid");
                }

                    if (singUpDTO.getPassword().length() <= 8 && singUpDTO.getPassword().length() <= 20) {
                        myList.add("Password length must be between 8 and 20 characters in length");
                    }

                    if (!(singUpDTO.getPassword().matches(".*[0-9].*"))) {
                        myList.add("Password must contain numbers");
                    }
                    if ((!singUpDTO.getPassword().matches(".*[@$#^()+=_.!%*?&].*"))) {
                        myList.add("Password must contain special Charter");
                    }
                    if ((!singUpDTO.getPassword().matches(".*[a-z].*"))) {
                        myList.add("Password must contain at least one lowercase letter");
                    }
                    if ((!singUpDTO.getPassword().matches(".*[A-Z].*"))) {
                        myList.add("Password must contain at least one uppercase letter");
                    }





            }




            /////////////////////////// Email ///////////////////////////
            if (singUpDTO.getEmail().isEmpty()) {
                myList.add("Email cannot by empty");
            } else {
                if (personRepository.existsByEmail(singUpDTO.getEmail())) {
                    myList.add("Email already in use.");
                }
                else{
                    if(singUpDTO.getEmail().matches(  "^[a-zA-Z0-9]+@gmail\\.com$")  )
                    {
                        Person.setEmail(singUpDTO.getEmail());
                        myList.add("Email is Valid");
                    }
                    else{
                        myList.add("Email is must end with @gmail.com and contain only uppercase[A-Z] and lowercase[a-z] letters and numbers[0-9]");
                    }
                }
            }
        /////////////////////////////   ConfirmPassword    //////////////////////////////

        if (singUpDTO.getConfirmPassword().isEmpty()) {
            myList.add("ConfirmPassword cannot by empty");
        } else {

            if (singUpDTO.getConfirmPassword().equals(singUpDTO.getPassword()) && !singUpDTO.getConfirmPassword().isEmpty() && !singUpDTO.getPassword().isEmpty()) {
               if(  Person.getEmail() != null) {
                   personRepository.save(Person);
                   PersonDTO PersonDTO = PersonMapper.mapToPersonDTO(Person);
                   myList.add(PersonDTO);
                   myList.add("ConfirmPassword is Valid");
               }
            } else {
                myList.add("Passwords do not match");
            }
        }

            ////////////////////////////////////////////////////////

            ///////////////////////////////////////////////////


            return myList;
        }



    @Override
    public PersonDTO getPersonByID(Long id) {
        Person Person = personRepository.findById(id).orElseThrow();
        return PersonMapper.mapToPersonDTO(Person);
    }

    @Override
    public List<PersonDTO> getAllPersons() {
        List<Person> Persons = personRepository.findAll();
        List<PersonDTO> PersonDTO = new ArrayList<>();
        for (int i = 0; i < Persons.size(); i++) {
            PersonDTO.add(PersonMapper.mapToPersonDTO(Persons.get(i)));

        }
        return PersonDTO;
    }

    @Override
    public PersonDTO updatePerson(Long id, PersonDTO PersonDTO) {
        Person Person = PersonMapper.mapToPerson(getPersonByID(id));
        Person.setUsername(PersonDTO.getUsername());
        Person.setPassword(PersonDTO.getPassword());
        Person.setEmail(PersonDTO.getEmail());
        personRepository.save(Person);
        return PersonMapper.mapToPersonDTO(Person);
    }

    @Override
    public void deletePerson(Long id) {
        Person Person = PersonMapper.mapToPerson(getPersonByID(id));
        personRepository.delete(Person);

    }

    @Override
    public Optional<PersonDTO> logIn(loginDTO loginDTO) {
        Optional<Person> person = personRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (person.isPresent()) {
            return Optional.of(PersonMapper.mapToPersonDTO(person.get()));
        }
        return Optional.empty();
    }
}