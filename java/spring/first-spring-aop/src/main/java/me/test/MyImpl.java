
package me.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MyImpl implements MyInterface {

    private final Logger logger = LoggerFactory.getLogger(MyImpl.class);

    final public void aa() {

        logger.info("aa()");

    }

    public void bb() {

        logger.info("bb()");
        aa();
        cc();

    }

    public void cc() {

        logger.info("cc()");

    }

}
