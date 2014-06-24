package me.test

/**
 * Created by zll on 14-6-24.
 */
enum MsgQueueEnum {
    HELLO_QUEUE ( "HELLO_QUEUE" );

    private final String queueName;

    MsgQueueEnum(String queueName) {
        this.queueName = queueName;
    }

    String getQueueName() {
        return queueName
    }
}
