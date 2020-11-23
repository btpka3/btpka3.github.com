package me.test.first.spring.boot.test;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

/**
 * @author dangqian.zll
 * @date 2020-04-15
 */
public class ObjectIdTest {

    @Test
    public void test01() {
        ObjectId oid = new ObjectId();
        System.out.println(oid);
        System.out.println(UUID.randomUUID());
    }
}
