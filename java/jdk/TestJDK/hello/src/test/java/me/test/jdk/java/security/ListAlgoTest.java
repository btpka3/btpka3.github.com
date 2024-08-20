package me.test.jdk.java.security;

import org.junit.jupiter.api.Test;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author dangqian.zll
 * @date 2024/8/19
 */
public class ListAlgoTest {

    @Test
    public void listAlgo01() {
        Set<String> algs = new TreeSet<>();
        for (Provider provider : Security.getProviders()) {
            provider.getServices().stream()
                    .filter(s -> "Cipher".equals(s.getType()))
                    .map(Service::getAlgorithm)
                    .forEach(algs::add);
        }
        algs.forEach(System.out::println);
    }

    @Test
    public void listProviders() {
        Set<String> s = new TreeSet<>();
        for (Provider provider : Security.getProviders()) {
            s.add(provider.getName());
        }
        s.forEach(System.out::println);
    }
}
