package me.test.rxjava.operators.connectable;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.flowables.ConnectableFlowable;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class Publish {

    @Test
    public void publish01() {
        System.out.println("----------------------publish01");

        ConnectableFlowable<Integer>  cf = Flowable.fromArray(1, 3, 5, 7, 9)
                .publish();

    }


}
