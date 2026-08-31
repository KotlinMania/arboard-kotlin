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
 *
 * Linux has a concept of clipboard "selections" which tend to be used in different contexts. This
 * enum provides a way to get/set to a specific clipboard (the default
 * `Clipboard` being used for the common platform API).
 */
public enum class LinuxClipboardKind {
    /**
     * Typically used selection for explicit cut/copy/paste actions (ie. windows/macos like
     * clipboard behavior).
     */
    Clipboard,

    /**
     * Typically used for mouse selections and/or currently selected text. Accessible via middle
     * mouse click.
     */
    Primary,

    /**
     * The secondary clipboard is rarely used but theoretically available on X11.
     */
    Secondary,
}

/**
 * The OS independent class for accessing the clipboard.
 *
 * Any number of `Clipboard` instances are allowed to exist at a single point in time.
 *
 * # Platform-specific behavior
 *
 * `arboard` does its best to abstract over different platforms, but sometimes the platform-specific
 * behavior leaks through unsolvably.
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

    /**
     * Fetches UTF-8 text from the clipboard and returns it.
     *
     * @throws Error if clipboard is empty or contents are not UTF-8 text.
     */
    public fun getText(): String = get().text()

    /**
     * Places the text onto the clipboard. Any valid UTF-8 string is accepted.
     */
    public fun setText(text: String) {
        set().text(text)
    }

    /**
     * Places the HTML as well as a plain-text alternative onto the clipboard.
     *
     * Any valid UTF-8 string is accepted.
     */
    public fun setHtml(
        html: String,
        altText: String? = null,
    ) {
        set().html(html, altText)
    }

    /**
     * Fetches image data from the clipboard, and returns the decoded pixels.
     *
     * @throws Error if clipboard is empty, contents are not an image, or cannot be converted.
     */
    public fun getImage(): ImageData = get().image()

    /**
     * Places an image onto the clipboard.
     */
    public fun setImage(image: ImageData) {
        set().image(image)
    }

    /**
     * Clears any contents that may be present from the platform's default clipboard,
     * regardless of the format of the data.
     */
    public fun clear() {
        clearWith().default()
    }

    /**
     * Begins a "clear" option to remove data from the clipboard.
     */
    public fun clearWith(): Clear = Clear(this)

    /**
     * Begins a "get" operation to retrieve data from the clipboard.
     */
    public fun get(): Get = Get(this)

    /**
     * Begins a "set" operation to set the clipboard's contents.
     */
    public fun set(): Set = Set(this)

    public fun clearKind(kind: LinuxClipboardKind) {
        contents.remove(kind)
    }

    internal fun getData(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): ClipboardData? = contents[kind]

    internal fun getOrCreate(kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard): ClipboardData =
        getOrCreateData(kind)

    public companion object {
        /**
         * Creates an instance of the clipboard.
         */
        public fun create(): Clipboard = Clipboard()

        /**
         * Creates an instance of the clipboard.
         */
        public fun new(): Clipboard = create()
    }
}

/**
 * A builder for an operation that gets a value from the clipboard.
 */
public class Get(
    private val clipboard: Clipboard,
    private var kind: LinuxClipboardKind = LinuxClipboardKind.Clipboard,
) : Private.Sealed {
    /**
     * Sets the clipboard the operation will retrieve data from.
     */
    public fun clipboard(kind: LinuxClipboardKind): Get {
        this.kind = kind
        return this
    }

    /**
     * Completes the "get" operation by fetching UTF-8 text from the clipboard.
     */
    public fun text(): String {
        val data = clipboard.getData(kind)
        return data?.text ?: data?.altText ?: throw Error.ContentNotAvailable
    }

    /**
     * Completes the "get" operation by fetching image data from the clipboard and returning the
     * decoded pixels.
     */
    public fun image(): ImageData {
        val data = clipboard.getData(kind)
        return data?.image ?: throw Error.ContentNotAvailable
    }

    /**
     * Completes the "get" operation by fetching HTML from the clipboard.
     */
    public fun html(): String {
        val data = clipboard.getData(kind)
        return data?.html ?: throw Error.ContentNotAvailable
    }

    /**
     * Completes the "get" operation by fetching a list of file paths from the clipboard.
     */
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
) : Private.Sealed {
    private var excludeFromHistory: Boolean = false

    /**
     * Sets the clipboard the operation will store data to.
     */
    public fun clipboard(kind: LinuxClipboardKind): Set {
        this.kind = kind
        return this
    }

    /**
     * Instructs the operation to wait until ownership is transferred.
     */
    public fun wait(): Set {
        return this
    }

    /**
     * Instructs the clipboard to exclude the data from history.
     */
    public fun excludeFromHistory(exclude: Boolean = true): Set {
        this.excludeFromHistory = exclude
        return this
    }

    /**
     * Completes the "set" operation by placing text onto the clipboard.
     */
    public fun text(text: String) {
        val data = clipboard.getOrCreate(kind)
        data.text = text
        data.html = null
        data.altText = null
        data.image = null
        data.fileList = emptyList()
    }

    /**
     * Completes the "set" operation by placing HTML as well as a plain-text alternative onto the
     * clipboard.
     */
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

    /**
     * Completes the "set" operation by placing an image onto the clipboard.
     */
    public fun image(image: ImageData) {
        val data = clipboard.getOrCreate(kind)
        data.image = image
        data.text = null
        data.html = null
        data.altText = null
        data.fileList = emptyList()
    }

    /**
     * Completes the "set" operation by placing a list of file paths onto the clipboard.
     */
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
) : Private.Sealed {
    /**
     * Sets the clipboard the operation will clear.
     */
    public fun clipboard(kind: LinuxClipboardKind): Clear {
        this.kind = kind
        return this
    }

    /**
     * Completes the "clear" operation by deleting any existing clipboard data.
     */
    public fun default() {
        clipboard.clearKind(kind)
    }
}
