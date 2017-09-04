package me.test.rxjava;

import io.reactivex.*;
import io.reactivex.schedulers.*;

import java.time.*;
import java.util.*;

/**
 *
 */
public class Tpl {

    public static void main(String[] args) {

        test00();

    }

    public static void test00() {
        System.out.println("----------------------test00");
        Flowable.just("Hello world").subscribe(System.out::println);
    }

}
