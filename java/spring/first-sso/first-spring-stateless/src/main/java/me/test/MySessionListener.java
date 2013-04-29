package me.test;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se) {
        Logger logger = LoggerFactory.getLogger(MySessionListener.class);
        logger.error("========= Session with ID=[" + se.getSession().getId() + "] is created, This should not happend.");
    }

    public void sessionDestroyed(HttpSessionEvent se) {

    }
}
