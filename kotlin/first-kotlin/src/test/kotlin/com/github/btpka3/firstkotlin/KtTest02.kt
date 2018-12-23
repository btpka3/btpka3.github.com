package com.github.btpka3.firstkotlin

import org.junit.Assert
import org.junit.Test

class KtTest02 {

    @Test
    fun x() {
        val result = listOf(1, 2, 3, 4, 5)
                .map { n -> n * n }
                .filter { n -> n < 10 }
                .first()
        Assert.assertEquals(1, 1);
    }
}