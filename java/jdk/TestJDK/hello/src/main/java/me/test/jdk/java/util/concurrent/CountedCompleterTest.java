package me.test.jdk.java.util.concurrent;

import java.util.concurrent.*;
import java.util.function.*;

public class CountedCompleterTest {

    public static void main(String[] args) throws Exception {

        // FIXME
    }

    public static <E> void forEach(E[] array, Consumer<E> action) {
        class Task extends CountedCompleter<Void> {
            final int lo, hi;

            Task(Task parent, int lo, int hi) {
                super(parent);
                this.lo = lo;
                this.hi = hi;
            }

            @Override
            public void compute() {
                if (hi - lo >= 2) {
                    int mid = (lo + hi) >>> 1;
                    // must set pending count before fork
                    setPendingCount(2);
                    new Task(this, mid, hi).fork(); // right child
                    new Task(this, lo, mid).fork(); // left child
                } else if (hi > lo) {
                    action.accept(array[lo]);
                }
                tryComplete();
            }
        }
        new Task(null, 0, array.length).invoke();
    }
}
