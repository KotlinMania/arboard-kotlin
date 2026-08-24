// port-lint: tests lib.rs
/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class LibTest {
    @Test
    fun allTests() {
        testTextSetAndGet()
        testUtf8Text()
        testClear()
        testHtmlWithoutAltText()
        testHtmlWithAltText()
        testFileList()
        testImageData()
        testLinuxClipboardKinds()
        testMultipleClipboards()
    }

    @Test
    fun multipleClipboardsAtOnce() {
        val clipboards = (1..10).map { Clipboard.new() }
        for ((idx, ctx) in clipboards.withIndex()) {
            ctx.setText("content $idx")
            assertEquals("content $idx", ctx.getText())
        }
    }

    @Test
    fun clipboardTraitConsistently() {
        assertSendSync()
    }

    @Test
    fun assertSendSync() {
        val ctx = Clipboard.new()
        assertNotNull(ctx)
    }

    @Test
    fun testTextSetAndGet() {
        val ctx = Clipboard.create()
        val text = "some string"
        ctx.setText(text)
        assertEquals(text, ctx.getText())
    }

    @Test
    fun testUtf8Text() {
        val ctx = Clipboard.create()
        val text = "Some utf8: 🤓 ∑φ(n)<ε 🐔"
        ctx.setText(text)
        assertEquals(text, ctx.getText())
    }

    @Test
    fun testClear() {
        val ctx = Clipboard.create()
        ctx.setText("hello world")
        assertEquals("hello world", ctx.getText())

        ctx.clear()
        assertFailsWith<Error.ContentNotAvailable> {
            ctx.getText()
        }

        // Clearing an already empty clipboard should succeed
        ctx.clear()
    }

    @Test
    fun testHtmlWithoutAltText() {
        val ctx = Clipboard.create()
        val html = "<b>hello</b> <i>world</i>!"
        ctx.setHtml(html, null)

        assertFailsWith<Error.ContentNotAvailable> {
            ctx.getText()
        }
        assertEquals(html, ctx.get().html())
    }

    @Test
    fun testHtmlWithAltText() {
        val ctx = Clipboard.create()
        val html = "<b>hello</b> <i>world</i>!"
        val altText = "hello world!"
        ctx.setHtml(html, altText)

        assertEquals(altText, ctx.getText())
        assertEquals(html, ctx.get().html())
    }

    @Test
    fun testFileList() {
        val ctx = Clipboard.create()
        val paths = listOf("README.md", "build.gradle.kts")
        ctx.set().fileList(paths)
        assertEquals(paths, ctx.get().fileList())
    }

    @Test
    fun testImageData() {
        val ctx = Clipboard.create()
        val bytes =
            byteArrayOf(
                255.toByte(),
                100,
                100,
                255.toByte(),
                100,
                255.toByte(),
                100,
                100,
                100,
                100,
                255.toByte(),
                100,
                0,
                0,
                0,
                255.toByte(),
            )
        val imgData = ImageData(width = 2, height = 2, bytes = bytes)

        // Setting image overwrites text
        ctx.setImage(imgData)
        assertFailsWith<Error.ContentNotAvailable> { ctx.getText() }

        // Setting text overwrites image
        ctx.setText("clipboard test")
        assertFailsWith<Error.ContentNotAvailable> { ctx.getImage() }

        // Setting image again can be read back
        ctx.setImage(imgData)
        val got = ctx.getImage()
        assertEquals(2, got.width)
        assertEquals(2, got.height)
        assertContentEquals(bytes, got.bytes)
    }

    @Test
    fun testLinuxClipboardKinds() {
        val ctx = Clipboard.create()
        val text1 = "I'm a little teapot,"
        val text2 = "short and stout,"
        val text3 = "here is my handle"

        ctx.set().clipboard(LinuxClipboardKind.Clipboard).text(text1)
        ctx.set().clipboard(LinuxClipboardKind.Primary).text(text2)
        ctx.set().clipboard(LinuxClipboardKind.Secondary).text(text3)

        assertEquals(text1, ctx.get().clipboard(LinuxClipboardKind.Clipboard).text())
        assertEquals(text2, ctx.get().clipboard(LinuxClipboardKind.Primary).text())
        assertEquals(text3, ctx.get().clipboard(LinuxClipboardKind.Secondary).text())
    }

    @Test
    fun testMultipleClipboards() {
        val c1 = Clipboard.create()
        val c2 = Clipboard.create()
        c1.setText("clip1")
        c2.setText("clip2")
        assertEquals("clip1", c1.getText())
        assertEquals("clip2", c2.getText())
    }
}
