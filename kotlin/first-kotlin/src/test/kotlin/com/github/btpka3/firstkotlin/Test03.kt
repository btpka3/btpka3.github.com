package com.github.btpka3.firstkotlin

import org.junit.Test
import java.util.stream.Collectors
import java.util.stream.IntStream
import java.util.stream.Stream

/**
 * @author 当千
 * @date 2018-11-09
 */
class Test03 {

    fun <T : Any> Stream<T>.toList(): List<T> = this.collect(Collectors.toList<T>())


    @Test
    fun test() {
        val intList = IntStream.range(0, 10)
                .flatMap { i -> IntStream.of(i * 10, i * 100) }
                .boxed()
                .toList();

        // https://stackoverflow.com/questions/35721528/how-can-i-call-collectcollectors-tolist-on-a-java-8-stream-in-kotlin
        //.collect<List<Int>, Any>(Collectors.toList<Int>())

        println(intList)
    }
}
