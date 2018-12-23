package com.github.btpka3.firstkotlin

import org.junit.Assert
import org.junit.Test

class KtTest01 {

    /**
     * |No.|name|type   |description|
     * |---|----|-------|-----------|
     * |1  |aa  |long   |   |
     * |2  |bb  |String |   |
     */
    @Test
    fun x() {
        val i = 1;
        val j = 2L;
        Assert.assertEquals(3L, i + j);
    }
}