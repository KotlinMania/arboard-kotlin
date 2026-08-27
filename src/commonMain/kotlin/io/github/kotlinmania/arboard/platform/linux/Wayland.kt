// port-lint: source platform/linux/wayland.rs
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
        public fun new(): WaylandClipboard = WaylandClipboard()
    }
}
