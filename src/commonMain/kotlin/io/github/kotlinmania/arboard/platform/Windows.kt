// port-lint: source arboard/src/platform/windows.rs
/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.platform

import io.github.kotlinmania.arboard.Error
import io.github.kotlinmania.arboard.ImageData

/**
 * Windows-specific extensions to the [io.github.kotlinmania.arboard.Set] builder.
 */
public interface SetExtWindows {
    /**
     * Completes the set operation and wait if required by the platform.
     */
    public fun wait(): SetExtWindows = this
}

/**
 * Platform clipboard implementation for Windows.
 */
public class WindowsClipboard internal constructor() {
    private var text: String? = null
    private var html: String? = null
    private var altText: String? = null
    private var image: ImageData? = null
    private var fileList: List<String> = emptyList()

    public fun clear() {
        text = null
        html = null
        altText = null
        image = null
        fileList = emptyList()
    }

    public fun text(): String = text ?: altText ?: throw Error.ContentNotAvailable

    public fun setText(value: String) {
        text = value
        html = null
        altText = null
        image = null
        fileList = emptyList()
    }

    public fun html(): String = html ?: throw Error.ContentNotAvailable

    public fun setHtml(value: String, alt: String?) {
        html = value
        altText = alt
        text = alt
        image = null
        fileList = emptyList()
    }

    public fun image(): ImageData = image ?: throw Error.ContentNotAvailable

    public fun setImage(value: ImageData) {
        image = value
        text = null
        html = null
        altText = null
        fileList = emptyList()
    }

    public fun fileList(): List<String> = fileList

    public fun setFileList(files: List<String>) {
        fileList = files.toList()
        text = null
        html = null
        altText = null
        image = null
    }

    public companion object {
        public fun new(): WindowsClipboard = WindowsClipboard()
    }
}
