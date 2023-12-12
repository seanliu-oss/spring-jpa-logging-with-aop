package org.sealiuoss.repo;

import org.sealiuoss.data.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PersonRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void savePerson(Person person) {
        String sql = "insert into person values (?,?,?)";
        Object[] args = {person.getId(), person.getFirstName(), person.getLastName()};
        jdbcTemplate.update(sql, args);
    }
}
