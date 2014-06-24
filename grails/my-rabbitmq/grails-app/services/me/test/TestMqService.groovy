package me.test

class TestMqService {

    static transactional = true

    int i = 0;

    def Map hello(Map msg) {
        i++
        //String reply = msg + " : " + i
        msg.put("user", msg.get("user")+i)
        println("=================reply : $msg")
        return msg
    }
}
