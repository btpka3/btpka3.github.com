package me.test.first.spring.boot.data.mongo.repo;

import me.test.first.spring.boot.data.mongo.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 *
 */
public interface UserRepository extends MyRepository<User, ObjectId> {
    public abstract Page<User> findByAgeIn(Collection<Integer> ages, Pageable pageable);
}
