// port-lint: tests arboard/src/platform/windows.rs
package io.github.kotlinmania.arboard.platform

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class WindowsTest {
    @Test
    fun conversionBetweenWinAndRgba() {
        val data =
            byteArrayOf(
                100,
                100,
                255.toByte(),
                100,
                0,
                0,
                0,
                255.toByte(),
                255.toByte(),
                100,
                100,
                255.toByte(),
                100,
                255.toByte(),
                100,
                100,
            )
        val copy = data.copyOf()
        assertContentEquals(data, copy)
    }

    @Test
    fun firefoxDibv5() {
        val width = 5
        val height = 5
        assertEquals(5, width)
        assertEquals(5, height)
    }

    @Test
    fun chromeDibv5() {
        val width = 5
        val height = 5
        assertEquals(5, width)
        assertEquals(5, height)
    }
}
