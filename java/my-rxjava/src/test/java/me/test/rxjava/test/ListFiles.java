package me.test.rxjava.test;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class ListFiles {


    public static Flowable<File> listFiles(String dir) {
        return Flowable.create(e -> {
            try {
                BlockingDeque<File> q = new LinkedBlockingDeque<>();
                q.add(new File(dir));

                while (q.peekFirst() != null) {
                    File f = q.poll();

                    if (!f.exists()) {
                        continue;
                    }

                    e.onNext(f);

                    if (f.isDirectory()) {
                        Arrays.stream(f.listFiles()).forEach(q::add);
                    }
                }
                e.onComplete();
            } catch (Throwable err) {
                e.onError(err);
            }
        }, BackpressureStrategy.BUFFER);
    }

    @Test
    public void test01() {
        listFiles("/tmp")
                .subscribe(System.out::println);

    }
}
