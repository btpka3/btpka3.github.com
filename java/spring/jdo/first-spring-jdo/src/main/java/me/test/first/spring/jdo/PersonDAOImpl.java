package me.test.first.spring.jdo;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

import me.test.first.spring.jdo.entity.Person;

public class PersonDAOImpl implements PersonDAO {

	private PersistenceManagerFactory pmFactory;

	@SuppressWarnings("unchecked")
	public List<Person> list() {
		PersistenceManager pm = this.pmFactory.getPersistenceManager();
		try {
			Query query = pm.newQuery(Person.class, "id > query_id ");
			query.declareParameters("Long query_id");
			return (List<Person>) query.execute(1);
		} finally {
			pm.close();
		}
	}

	public void insert(Person person) {
		PersistenceManager pm = this.pmFactory.getPersistenceManager();
		try {
			pm.makePersistent(person);
		} finally {
			pm.close();
		}
	}

	public Person select(String id) {
		PersistenceManager pm = this.pmFactory.getPersistenceManager();
		try {
			pm.getFetchPlan().addGroup("imgData");
			return pm.getObjectById(Person.class, Long.valueOf(id));
		} finally {
			pm.close();
		}
	}

	public void update(Map<String, Object> person) {
		PersistenceManager pm = this.pmFactory.getPersistenceManager();
		try {
			Person p = pm.getObjectById(Person.class, person.get("id"));
			if (person.containsKey("name")) {
				p.setName((String) person.get("name"));
			}
			if (person.containsKey("age")) {
				p.setAge((Integer) person.get("age"));
			}
			if (person.containsKey("male")) {
				p.setMale((Boolean) person.get("male"));
			}
			if (person.containsKey("birthday")) {
				p.setBirthday((Date) person.get("birthday"));
			}
			if (person.containsKey("imgData")) {
				p.setImgData((byte[]) person.get("imgData"));
			}
		} finally {
			pm.close();
		}
	}

	public void delete(long id) {
		PersistenceManager pm = this.pmFactory.getPersistenceManager();
		try {
			Person p = pm.getObjectById(Person.class, id);
			pm.deletePersistent(p);
		} finally {
			pm.close();
		}
	}

	public void setPersistenceManagerFactory(PersistenceManagerFactory pmf) {
		this.pmFactory = pmf;
	}

	public void destroy() {
		System.out.println("dddddddddddddddddddddddddddddddd");
	}
}
