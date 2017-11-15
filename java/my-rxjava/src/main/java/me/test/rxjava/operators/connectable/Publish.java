package me.test.rxjava.operators.connectable;

import io.reactivex.*;
import io.reactivex.flowables.*;

/**
 *
 */
public class Publish {


    public static void main(String[] args) {

        publish01();

    }


    public static void publish01() {
        System.out.println("----------------------publish01");

        ConnectableFlowable<Integer>  cf = Flowable.fromArray(1, 3, 5, 7, 9)
                .publish();

    }


}
