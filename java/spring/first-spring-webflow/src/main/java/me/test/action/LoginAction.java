
package me.test.action;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAction {

    private Logger logger = LoggerFactory.getLogger(LoginAction.class);

    public String checkPassword(String userName, String password) {

        if (StringUtils.isBlank(userName)) {
            logger.debug("userName is empty.");
            return "error";
        }
        if (userName.trim().equals(password)) {
            logger.debug("password is correct.");
            return "success";
        }
        logger.debug("password is wrong.");
        return "error";
    }

}
