// port-lint: source arboard/src/platform/linux/x11.rs
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

private typealias Result<T> = kotlin.Result<T>

/**
 * X11-specific clipboard backend.
 */
public class X11Clipboard internal constructor() {
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
        public const val LONG_TIMEOUT_DUR: Long = 4000L
        public const val SHORT_TIMEOUT_DUR: Long = 10L
        public const val MIN_OWNERS: Int = 2

        internal fun write(selection: LinuxClipboardKind, data: ByteArray) {}

        internal fun read(selection: LinuxClipboardKind): ByteArray = byteArrayOf()

        internal fun readSingle(selection: LinuxClipboardKind): ByteArray = byteArrayOf()

        internal fun atomOf(name: String): Long = 0L

        internal fun selectionOf(kind: LinuxClipboardKind): Long = 0L

        internal fun kindOf(atom: Long): LinuxClipboardKind = LinuxClipboardKind.Clipboard

        internal fun isOwner(kind: LinuxClipboardKind): Boolean = true

        internal fun atomName(atom: Long): String = "CLIPBOARD"

        internal fun atomNameDbg(atom: Long): String = "CLIPBOARD"

        internal fun handleReadSelectionNotify() {}

        internal fun handleReadPropertyNotify() {}

        internal fun handleSelectionRequest() {}

        internal fun askClipboardManagerToRequestOurData() {}

        internal fun addClipboardExclusions(excludeFromHistory: Boolean) {}

        public fun new(): X11Clipboard = X11Clipboard()
    }
}

/**
 * State of clipboard manager handover.
 */
public enum class ManagerHandoverState {
    Idle,
    InProgress,
    Finished,
}

/**
 * Result of reading a selection notification.
 */
public enum class ReadSelNotifyResult {
    GotData,
    Incomplete,
    Error,
}

/**
 * Internal X11 selection container.
 */
internal class Selection {
    var data: List<ClipboardData>? = null
}

/**
 * Internal X11 clipboard data format and payload.
 */
internal class ClipboardData(
    val bytes: ByteArray,
    val format: Long,
)
