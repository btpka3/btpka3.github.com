package me.test.my.spring.boot.mock.service;

import org.springframework.stereotype.*;

@Service
public class ServiceC {

    public String str(int i) {
        return Integer.toString(i) + 1;
    }
}
