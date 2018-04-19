package me;

import me.test.MyImpl;
import me.test.MyInterface;
import org.slf4j.Logger;

public class U {

    public static void logTest(Logger logger, Object obj) {


        if (!(obj instanceof MyInterface)) {
            logger.debug("---------------------- obj not instanceof MyInterface : " + obj.getClass());
            return;
        }
        MyInterface myInterface = (MyInterface) obj;

        logger.debug("---------------------- aa()");
        myInterface.aa();

        logger.debug("---------------------- bb()");
        myInterface.bb();


        if (!(obj instanceof MyImpl)) {
            logger.debug("---------------------- myInterface NOT instanceof MyImpl");
            return;
        }

        MyImpl myImpl = (MyImpl) obj;
        logger.debug("---------------------- cc()");
        myImpl.cc();
    }
}
