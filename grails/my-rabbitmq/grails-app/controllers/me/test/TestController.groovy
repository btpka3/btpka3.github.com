package me.test

class TestController {

    def helloMq
    def aMq

    def index = {
        def msg = [user:params.user?:"guest"]
        def reply = helloMq.convertSendAndReceive(msg)
        render reply.toString()

    }
}
