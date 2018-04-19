
package me.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("aaa")
public class MyImpl implements MyInterface {

    private final Logger logger = LoggerFactory.getLogger(MyImpl.class);

    @Override
    //final
    public void aa() {

        logger.info("aa()");

    }

    @Override
    public void bb() {

        logger.info("bb()");
        aa();
        cc();

    }

    public void cc() {

        logger.info("cc()");

    }

}
