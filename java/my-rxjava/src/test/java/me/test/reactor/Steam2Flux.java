package me.test.reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.BaseStream;

/**
 * @date 2018-11-05
 */
public class Steam2Flux {

    @Test
    public void test01() {
        /*
        echo -e "aaa\nbbb\nccc" > /tmp/a.txt
         */
        Flux.using(
                () -> Files.lines(Paths.get("/tmp/a.txt")),
                Flux::fromStream,
                BaseStream::close
        )
                .subscribe(System.out::println);
    }
}
