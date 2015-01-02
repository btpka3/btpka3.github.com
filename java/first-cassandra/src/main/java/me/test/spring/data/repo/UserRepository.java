package me.test.spring.data.repo;

import me.test.spring.data.domain.User;

import org.springframework.data.cassandra.repository.TypedIdCassandraRepository;

public interface UserRepository extends TypedIdCassandraRepository<User, User.Pk> {

    // FIXME InvalidDataAccessApiUsageException: declarative query methods are a todo
    // List<User> findByName(String name);
}
