package com.Application.Restaurant.Service.Impl;

import com.Application.Restaurant.DTO.*;
import com.Application.Restaurant.Exception.PasswordUpdateException;
import com.Application.Restaurant.entity.Person;
import com.Application.Restaurant.Mapper.PersonMapper;
import com.Application.Restaurant.Repository.personRepository;
import com.Application.Restaurant.Service.personService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements personService {
    private personRepository personRepository;

    @Override
    public List<Object> signUp(singUpDTO singUpDTO) {
        List<Object> myList = new ArrayList<>();
        Person Person = new Person();
        Person.setBio(singUpDTO.getBio());

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
            if (singUpDTO.getUsername().length() < 3 || singUpDTO.getUsername().length() > 15) {
                myList.add("Username length must be between 3 and 15 characters in length");
            }


            if (singUpDTO.getUsername().length() >= 3 && singUpDTO.getUsername().length() <= 15
                    && !singUpDTO.getUsername().matches(".*[0-9].*")
                    && !singUpDTO.getUsername().matches(".*[@$#^()_.!%*?&].*")) {

                Person.setUsername(singUpDTO.getUsername());

            }


        }


        //////////////////////////////

        ///////////////////////////  Password  /////////////////////////
        if (singUpDTO.getPassword().isEmpty()) {
            myList.add("Password cannot by empty");
        } else {
            if (isCheckPassword(singUpDTO.getPassword())) {
                Person.setPassword(singUpDTO.getPassword());

            }
            else{
                checkPassword(singUpDTO.getPassword());
            }
        }


        /////////////////////////// Email ///////////////////////////
        if (singUpDTO.getEmail().isEmpty()) {
            myList.add("Email cannot by empty");
        } else {
            if (personRepository.existsByEmail(singUpDTO.getEmail())) {
                myList.add("Email already in use.");
            } else {
                if (singUpDTO.getEmail().matches("^[a-zA-Z0-9]+@gmail\\.com$")) {
                    Person.setEmail(singUpDTO.getEmail());

                } else {
                    myList.add("Email is must end with @gmail.com and contain only uppercase[A-Z] and lowercase[a-z] letters and numbers[0-9]");
                }
            }
        }
        /////////////////////////////   ConfirmPassword    //////////////////////////////

        if (singUpDTO.getConfirmPassword().isEmpty()) {
            myList.add("ConfirmPassword cannot by empty");
        } else {

            if (singUpDTO.getConfirmPassword().equals(singUpDTO.getPassword()) && !singUpDTO.getConfirmPassword().isEmpty() && !singUpDTO.getPassword().isEmpty()) {
                if (Person.getEmail() != null) {
                    personRepository.save(Person);
                    PersonDTO PersonDTO = PersonMapper.mapToPersonDTO(Person);
                    myList.add(PersonDTO);

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
    public List<Object> logIn(loginDTO loginDTO) {
        List<Object> myList = new ArrayList<>();


        /////////////////////////// Email ///////////////////////////
        if (loginDTO.getEmail().isEmpty()) {
            myList.add("Email cannot by empty");
        } else {
            if (personRepository.existsByEmail(loginDTO.getEmail())) {
                myList.add("Email is found");
                ///////////////Password////////////
                if (loginDTO.getPassword().isEmpty()) {
                    myList.add("Password cannot by empty");
                } else {
                    if (personRepository.existsByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword())) {
                        myList.add("Password is correct");
                        Person Person = personRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
                        myList.add(PersonMapper.mapToPersonDTO(Person));

                    } else {
                        myList.add("Password is incorrect,please try again");
                    }
                }


            } else {
                if (loginDTO.getEmail().matches(".*@gmail\\.com$")) {
                    if (!personRepository.existsByEmail(loginDTO.getEmail())) {
                        myList.add("Email is not found");
                    }
                } else {
                    myList.add("Email is must end with @gmail.com");
                }
            }
        }

        return myList;
    }


    @Override
    public PersonDTO readPerson(Long id) {
        Person Person = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Person with ID " + id + " not found"));
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
    public void updatePerson(Long id, updatePersonDTO updatePersonDTO) {
        Person Person = PersonMapper.mapToPerson(readPerson(id));
        Person.setUsername(updatePersonDTO.getUsername());
        Person.setBio(updatePersonDTO.getBio());
        Person.setEmail(updatePersonDTO.getEmail());
        personRepository.save(Person);
    }

    @Override
    public void deletePerson(Long id) {
       // Person Person = PersonMapper.mapToPerson(getPersonByID(id));
        Person person = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Person with ID " + id + " not found"));
        personRepository.delete(person);

    }

    @Override
    public void changePassword(Long idPerson, changePasswordDTO changePasswordDTO) {
        Person person = personRepository.findById(idPerson).orElseThrow(() -> new NoSuchElementException("Person with ID " + idPerson + " not found"));
        if (changePasswordDTO.getOldPassword().equals(person.getPassword())) {
            if (changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword())) {
                if (isCheckPassword(changePasswordDTO.getNewPassword())) {
                    person.setPassword(changePasswordDTO.getNewPassword());
                    personRepository.save(person);
                } else {
                    checkPassword(changePasswordDTO.getNewPassword());
                }

            } else {
                throw new PasswordUpdateException("New password and ConfirmNewPassword do not match.");
            }
        } else {
            throw new PasswordUpdateException("Old password is incorrect.");
        }

    }

    public boolean isCheckPassword(String password) {
        if (password.length() >= 8 && password.length() <= 20
                && password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\\\"\\|,.<>/?].*")

        ) {
            return true;
        }

        return false;
    }

    public List<String>  checkPassword(String password) {
        List<String> errors = new ArrayList<>();
        if (password.length() <= 8 && password.length() <= 20) {
            errors.add("Password length must be between 8 and 20 characters in length");
        }

        if (!(password.matches(".*[0-9].*"))) {
            errors.add("Password must contain numbers");
        }
        if ((!password.matches(".*[@$#^()+=_.!%*?&].*"))) {
            errors.add("Password must contain special Charter");
        }
        if ((!password.matches(".*[a-z].*"))) {
            errors.add("Password must contain at least one lowercase letter");
        }
        if ((!password.matches(".*[A-Z].*"))) {
            errors.add("Password must contain at least one uppercase letter");
        }

        return errors;
    }
}

     /*   Optional<Person> person = personRepository.findByEmailAndPassword(loginDTO.getEmail(), loginDTO.getPassword());
        if (person.isPresent()) {
            return Optional.of(PersonMapper.mapToPersonDTO(person.get()));
        }
        return Optional.empty();
    }*/
