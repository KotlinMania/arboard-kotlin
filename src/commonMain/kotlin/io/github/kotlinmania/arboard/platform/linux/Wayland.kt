// port-lint: source arboard/src/platform/linux/wayland.rs
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
import io.github.kotlinmania.arboard.LinuxClipboardKind

/**
 * Wayland-specific clipboard backend.
 */
public class WaylandClipboard internal constructor() {
    private val contents = mutableMapOf<LinuxClipboardKind, Entry>()

    private data class Entry(
        var text: String? = null,
        var html: String? = null,
        var altText: String? = null,
        var image: ImageData? = null,
        var fileList: List<String> = emptyList(),
    )

    private fun getOrCreate(kind: LinuxClipboardKind): Entry =
        contents.getOrPut(kind) { Entry() }

    public fun clear(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard) {
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

    public fun getText(selection: LinuxClipboardKind = LinuxClipboardKind.Clipboard): String =
        text(selection)

    public fun getHtml(selection: LinuxClipboardKind = LinuxClipboardKind.Clipboard): String =
        html(selection)

    public fun getImage(selection: LinuxClipboardKind = LinuxClipboardKind.Clipboard): ImageData =
        image(selection)

    public fun fileList(selection: LinuxClipboardKind = LinuxClipboardKind.Clipboard): List<String> {
        val entry = contents[selection] ?: throw Error.ContentNotAvailable
        return entry.fileList
    }

    public fun getFileList(selection: LinuxClipboardKind = LinuxClipboardKind.Clipboard): List<String> =
        fileList(selection)

    public fun setFileList(files: List<String>, kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard) {
        val entry = getOrCreate(kind)
        entry.fileList = files.toList()
        entry.text = null
        entry.html = null
        entry.altText = null
        entry.image = null
    }

    public fun drop() {
        contents.clear()
    }

    public companion object {
        public const val MIME_PNG: String = "image/png"
        public const val MIME_URI: String = "text/uri-list"

        internal fun addClipboardExclusions(excludeFromHistory: Boolean) {}

        internal fun handleCopyError(e: Throwable): Error =
            if (e is Error) e else Error.Unknown(e.message ?: "Wayland copy error")

        internal fun handlePasteError(e: Throwable): Error =
            if (e is Error) e else Error.Unknown(e.message ?: "Wayland paste error")

        internal fun tryInto(selection: LinuxClipboardKind): LinuxClipboardKind = selection

        public fun new(): WaylandClipboard = WaylandClipboard()
    }
}
