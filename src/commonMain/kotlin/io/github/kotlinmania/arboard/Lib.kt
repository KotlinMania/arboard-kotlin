// port-lint: source lib.rs
/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard

/**
 * The kind of Linux clipboard to interact with.
 */
public enum class LinuxClipboardKind {
    Clipboard,
    Primary,
    Secondary,
}

/**
 * The OS independent class for accessing the clipboard.
 */
public class Clipboard {
    private val contents = mutableMapOf<LinuxClipboardKind, ClipboardData>()

    internal data class ClipboardData(
        var text: String? = null,
        var html: String? = null,
        var altText: String? = null,
        var image: ImageData? = null,
        var fileList: List<String> = emptyList(),
    )

    private fun getOrCreateData(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): ClipboardData =
        contents.getOrPut(kind) { ClipboardData() }

    public fun getText(): String = get().text()

    public fun setText(text: String) {
        set().text(text)
    }

    public fun setHtml(
        html: String,
        altText: String? = null,
    ) {
        set().html(html, altText)
    }

    public fun getImage(): ImageData = get().image()

    public fun setImage(image: ImageData) {
        set().image(image)
    }

    public fun clear() {
        clearWith().default()
    }

    public fun clearWith(): Clear = Clear(this)

    public fun get(): Get = Get(this)

    public fun set(): Set = Set(this)

    public fun clearKind(kind: LinuxClipboardKind) {
        contents.remove(kind)
    }

    internal fun getData(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): ClipboardData? = contents[kind]

    internal fun getOrCreate(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): ClipboardData =
        getOrCreateData(kind)

    public companion object {
        public fun create(): Clipboard = Clipboard()
    }
}

/**
 * A builder for an operation that gets a value from the clipboard.
 */
public class Get(
    private val clipboard: Clipboard,
    private var kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard,
) {
    public fun clipboard(kind: LinuxClipboardKind): Get {
        this.kind = kind
        return this
    }

    public fun text(): String {
        val data = clipboard.getData(kind)
        return data?.text ?: data?.altText ?: throw Error.ContentNotAvailable
    }

    public fun image(): ImageData {
        val data = clipboard.getData(kind)
        return data?.image ?: throw Error.ContentNotAvailable
    }

    public fun html(): String {
        val data = clipboard.getData(kind)
        return data?.html ?: throw Error.ContentNotAvailable
    }

    public fun fileList(): List<String> {
        val data = clipboard.getData(kind)
        return data?.fileList ?: throw Error.ContentNotAvailable
    }
}

/**
 * A builder for an operation that sets a value to the clipboard.
 */
public class Set(
    private val clipboard: Clipboard,
    private var kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard,
) {
    public fun clipboard(kind: LinuxClipboardKind): Set {
        this.kind = kind
        return this
    }

    public fun wait(): Set = this

    public fun text(text: String) {
        val data = clipboard.getOrCreate(kind)
        data.text = text
        data.html = null
        data.altText = null
        data.image = null
        data.fileList = emptyList()
    }

    public fun html(
        html: String,
        altText: String? = null,
    ) {
        val data = clipboard.getOrCreate(kind)
        data.html = html
        data.altText = altText
        data.text = altText
        data.image = null
        data.fileList = emptyList()
    }

    public fun image(image: ImageData) {
        val data = clipboard.getOrCreate(kind)
        data.image = image
        data.text = null
        data.html = null
        data.altText = null
        data.fileList = emptyList()
    }

    public fun fileList(fileList: List<String>) {
        val data = clipboard.getOrCreate(kind)
        data.fileList = fileList.toList()
        data.text = null
        data.html = null
        data.altText = null
        data.image = null
    }
}

/**
 * A builder for an operation that clears the data from the clipboard.
 */
public class Clear(
    private val clipboard: Clipboard,
    private var kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard,
) {
    public fun clipboard(kind: LinuxClipboardKind): Clear {
        this.kind = kind
        return this
    }

    public fun default() {
        clipboard.clearKind(kind)
    }
}
