package com.github.btpka3.first.spring.data.jpa;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {UtApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@Transactional
@Rollback
public abstract class BaseTest {
    final Logger log = LoggerFactory.getLogger(getClass());
}
