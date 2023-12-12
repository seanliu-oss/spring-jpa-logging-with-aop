package org.sealiuoss.service;

import org.sealiuoss.data.Person;
import org.sealiuoss.repo.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    @Autowired
    PersonRepository personRepository;

    @Transactional
    public void savePersons(){
        for(int i = 1; i<=10; i++){
            Person person = new Person(i,"user"+i,"test" );
            personRepository.savePerson(person);
        }
        throw new RuntimeException("abort");
    }
}
