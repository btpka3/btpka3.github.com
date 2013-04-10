package me.test.maven.first.jar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext-Dao.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class MainTest {

    @Autowired
    private Main main;

    @Before
    public void before() {
    }

    @BeforeTransaction
    public void beforeTransaction() {
    }

    @AfterTransaction
    public void afterTransaction() {
    }

    @After
    public void after() {
    }

    @Test
    public void testAdd01() {
    }

}
