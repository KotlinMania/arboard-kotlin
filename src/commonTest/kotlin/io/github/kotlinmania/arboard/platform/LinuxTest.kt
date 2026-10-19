/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.platform

import io.github.kotlinmania.arboard.platform.linux.LinuxClipboard
import kotlin.test.Test
import kotlin.test.assertEquals

class LinuxTest {
    @Test
    fun testDecodingUriList() {
        val fileList =
            listOf(
                "file:///tmp/bar.log",
                "file:///tmp/test%5C.txt",
                "file:///tmp/foo%3F.png",
                "file:///tmp/white%20space.txt",
            )
        val expected =
            listOf(
                "/tmp/bar.log",
                "/tmp/test\\.txt",
                "/tmp/foo?.png",
                "/tmp/white space.txt",
            )
        val decoded = LinuxClipboard.pathsFromUriList(fileList.joinToString("\n").encodeToByteArray())
        assertEquals(expected, decoded)
    }
}
