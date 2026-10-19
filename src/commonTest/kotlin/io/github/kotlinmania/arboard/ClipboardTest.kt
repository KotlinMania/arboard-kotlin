/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard

import io.github.kotlinmania.arboard.platform.linux.LinuxClipboardKind
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class ClipboardTest {
    companion object {
        private const val TEXT1: String = "I'm a little teapot,"
        private const val TEXT2: String = "short and stout,"
        private const val TEXT3: String = "here is my handle"
        private const val THREAD_COUNT: Int = 10
    }

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
        val clipboards = (1..THREAD_COUNT).map { Clipboard.new() }
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
        val html = "<h1>Title</h1>"
        val altText = "Title"
        ctx.setHtml(html, altText)

        assertEquals(altText, ctx.getText())
        assertEquals(html, ctx.get().html())
    }

    @Test
    fun testFileList() {
        val ctx = Clipboard.create()
        val files = listOf("/path/to/file1.txt", "/path/to/file2.txt")
        ctx.set().fileList(files)

        val retrieved = ctx.get().fileList()
        assertEquals(files, retrieved)
    }

    @Test
    fun testImageData() {
        val ctx = Clipboard.create()
        val bytes =
            byteArrayOf(
                255.toByte(),
                0,
                0,
                255.toByte(),
                0,
                255.toByte(),
                0,
                255.toByte(),
                0,
                0,
                255.toByte(),
                255.toByte(),
                255.toByte(),
                255.toByte(),
                0,
                255.toByte(),
            )
        val image =
            ImageData(
                width = 2,
                height = 2,
                bytes = bytes,
            )

        ctx.setImage(image)
        val retrieved = ctx.getImage()
        assertEquals(image.width, retrieved.width)
        assertEquals(image.height, retrieved.height)
        assertContentEquals(image.bytes, retrieved.bytes)
    }

    @Test
    fun testLinuxClipboardKinds() {
        val ctx = Clipboard.create()

        ctx.set().clipboard(LinuxClipboardKind.Clipboard).text(TEXT1)
        ctx.set().clipboard(LinuxClipboardKind.Primary).text(TEXT2)
        ctx.set().clipboard(LinuxClipboardKind.Secondary).text(TEXT3)

        assertEquals(TEXT1, ctx.get().clipboard(LinuxClipboardKind.Clipboard).text())
        assertEquals(TEXT2, ctx.get().clipboard(LinuxClipboardKind.Primary).text())
        assertEquals(TEXT3, ctx.get().clipboard(LinuxClipboardKind.Secondary).text())

        ctx.clearWith().clipboard(LinuxClipboardKind.Primary).default()
        assertFailsWith<Error.ContentNotAvailable> {
            ctx.get().clipboard(LinuxClipboardKind.Primary).text()
        }
        assertEquals(TEXT1, ctx.get().clipboard(LinuxClipboardKind.Clipboard).text())
        assertEquals(TEXT3, ctx.get().clipboard(LinuxClipboardKind.Secondary).text())
    }

    @Test
    fun testMultipleClipboards() {
        val ctx1 = Clipboard.create()
        val ctx2 = Clipboard.create()

        ctx1.setText("from ctx1")
        assertEquals("from ctx1", ctx2.getText())

        ctx2.setText("from ctx2")
        assertEquals("from ctx2", ctx1.getText())
    }
}
