package me.test.my.spring.boot.mock.service;

import org.springframework.stereotype.*;

@Service
public class ServiceB {

    public int add(int i) {
        return i + 1;
    }
}
