/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.platform.linux

import io.github.kotlinmania.arboard.Error
import io.github.kotlinmania.arboard.ImageData
import io.github.kotlinmania.image.ExtendedColorType
import io.github.kotlinmania.image.codecs.png.PngEncoder
import io.github.kotlinmania.image.io.BufferIoWrite

/**
 * Converts an error description into an Unknown error.
 */
public fun intoUnknown(error: Any): Error = Error.Unknown(error.toString())

/**
 * Encodes image data as PNG bytes.
 */
public fun encodeAsPng(image: ImageData): ByteArray {
    if (image.bytes.isEmpty() || image.width == 0 || image.height == 0) {
        throw Error.ConversionFailure
    }
    val out = BufferIoWrite()
    val encoder = PngEncoder(out)
    try {
        encoder.writeImage(
            image.bytes,
            image.width.toUInt(),
            image.height.toUInt(),
            ExtendedColorType.Rgba8,
        )
    } catch (_: Exception) {
        throw Error.ConversionFailure
    }
    return out.toByteArray()
}

/**
 * Platform clipboard implementation for Linux.
 */
public class LinuxClipboard internal constructor() {
    private val contents = mutableMapOf<LinuxClipboardKind, Entry>()

    private data class Entry(
        var text: String? = null,
        var html: String? = null,
        var altText: String? = null,
        var image: ImageData? = null,
        var fileList: List<String> = emptyList(),
    )

    private fun getOrCreate(kind: LinuxClipboardKind): Entry = contents.getOrPut(kind) { Entry() }

    public fun clear(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard) {
        clearInner(kind)
    }

    private fun clearInner(kind: LinuxClipboardKind) {
        contents.remove(kind)
    }

    public fun text(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): String {
        val entry = contents[kind] ?: throw Error.ContentNotAvailable
        return entry.text ?: entry.altText ?: throw Error.ContentNotAvailable
    }

    public fun setText(value: String, kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard) {
        val entry = getOrCreate(kind)
        entry.text = value
        entry.html = null
        entry.altText = null
        entry.image = null
        entry.fileList = emptyList()
    }

    public fun html(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): String {
        val entry = contents[kind] ?: throw Error.ContentNotAvailable
        return entry.html ?: throw Error.ContentNotAvailable
    }

    public fun setHtml(value: String, alt: String?, kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard) {
        val entry = getOrCreate(kind)
        entry.html = value
        entry.altText = alt
        entry.text = alt
        entry.image = null
        entry.fileList = emptyList()
    }

    public fun image(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): ImageData {
        val entry = contents[kind] ?: throw Error.ContentNotAvailable
        return entry.image ?: throw Error.ContentNotAvailable
    }

    public fun setImage(value: ImageData, kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard) {
        val entry = getOrCreate(kind)
        entry.image = value
        entry.text = null
        entry.html = null
        entry.altText = null
        entry.fileList = emptyList()
    }

    public fun fileList(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): List<String> {
        val entry = contents[kind] ?: throw Error.ContentNotAvailable
        return entry.fileList
    }

    public fun setFileList(files: List<String>, kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard) {
        val entry = getOrCreate(kind)
        entry.fileList = files.toList()
        entry.text = null
        entry.html = null
        entry.altText = null
        entry.image = null
    }

    public companion object {
        public const val KDE_EXCLUSION_MIME: String = "x-kde-passwordManagerHint"
        public val KDE_EXCLUSION_HINT: ByteArray = byteArrayOf(115, 101, 99, 114, 101, 116) // "secret"
        public const val ASCII_SET: String = "#;?[] \"\\^`{}|"

        public fun pathsFromUriList(uriList: ByteArray): List<String> {
            val text = uriList.decodeToString()
            return text
                .split('\n')
                .filter { it.startsWith("file://") }
                .map { uri ->
                    uri
                        .removePrefix("file://")
                        .replace("%5C", "\\")
                        .replace("%3F", "?")
                        .replace("%20", " ")
                }
        }

        public fun pathsToUriList(fileList: List<String>): String {
            if (fileList.isEmpty()) throw Error.ConversionFailure
            return fileList.joinToString("\n") { "file://$it" }
        }

        public fun new(): LinuxClipboard = LinuxClipboard()
    }
}
