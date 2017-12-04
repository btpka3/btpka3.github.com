package me.test.first.spring.boot.batch.service;

import lombok.extern.slf4j.*;
import me.test.first.spring.boot.batch.domain.*;
import org.springframework.batch.item.*;
import org.springframework.stereotype.*;

@Component
@Slf4j
public class PersonItemProcessor implements ItemProcessor<Person, Person> {

    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(firstName, lastName);

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}