// port-lint: tests platform/linux/mod.rs
package io.github.kotlinmania.arboard.platform

import kotlin.test.Test
import kotlin.test.assertEquals

class LinuxTest {
    @Test
    fun testDecodingUriList() {
        val fileList = listOf(
            "file:///tmp/bar.log",
            "file:///tmp/test%5C.txt",
            "file:///tmp/foo%3F.png",
            "file:///tmp/white%20space.txt",
        )
        val expected = listOf(
            "/tmp/bar.log",
            "/tmp/test\\.txt",
            "/tmp/foo?.png",
            "/tmp/white space.txt",
        )
        val decoded = fileList.map { uri ->
            uri.removePrefix("file://")
                .replace("%5C", "\\")
                .replace("%3F", "?")
                .replace("%20", " ")
        }
        assertEquals(expected, decoded)
    }
}
