package me.test.first.spring.jdo;

import java.util.List;
import java.util.Map;

import me.test.first.spring.jdo.entity.Person;

public interface PersonDAO {
	List<Person> list();

	Person select(String id);

	void insert(Person person);

	void update(Map<String,Object> person);

	void delete(long id);
}
