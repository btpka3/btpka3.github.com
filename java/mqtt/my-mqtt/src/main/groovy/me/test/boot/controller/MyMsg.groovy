package me.test.boot.controller

import groovy.transform.ToString

@ToString(includeNames = true)
class MyMsg {

    String name
    Date birthday
    List<String> hobbies
    int age
    String memo
}
