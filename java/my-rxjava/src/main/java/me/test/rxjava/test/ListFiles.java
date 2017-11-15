package me.test.rxjava.test;

import io.reactivex.*;

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

    public static void main(String[] args) {
        listFiles("/tmp")
                .subscribe(System.out::println);

    }
}
