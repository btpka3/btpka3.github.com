package me.test.my.spring.boot.mock;

import org.junit.runner.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.context.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        classes = {MpUtApp.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public abstract class BaseTest {

    protected final String logPrefix = String.join("", Collections.nCopies(40, "█")) + " ";
    protected final Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    protected ApplicationContext applicationContext;

}
