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

    public fun open(): WindowsClipboard = this

    public companion object {
        public const val LCS_sRGB: UInt = 0x73524742u
        public const val NULL: Long = 0L
        public const val DEFAULT_OPEN_ATTEMPTS: Int = 5
        public const val FORMAT: String = "PNG"
        public const val DROPFILES_HEADER_SIZE: Int = 20
        public const val CLIPBOARD_EXCLUSION_DATA: String = "ExcludeClipboardContentFromMonitorProcessing"

        private val DATA: ByteArray =
            byteArrayOf(
                100,
                100,
                255.toByte(),
                100,
                0,
                0,
                0,
                255.toByte(),
                255.toByte(),
                100,
                100,
                255.toByte(),
                100,
                255.toByte(),
                100,
                100,
            )

        private val EXPECTED: ByteArray =
            byteArrayOf(
                107,
                89,
                42,
                255.toByte(),
                60,
                104,
                50,
                255.toByte(),
            )

        public fun new(): WindowsClipboard = WindowsClipboard()
    }
}

/**
 * An open clipboard session.
 */
public class OpenClipboard internal constructor(
    private val clipboard: WindowsClipboard,
)

/**
 * Representation of image pixel data either borrowed or owned.
 */
public sealed class ImageDataCow {
    public class Borrowed(
        public val pixels: UIntArray,
    ) : ImageDataCow()

    public class Owned(
        public val pixels: UIntArray,
    ) : ImageDataCow()
}

/**
 * Abstraction over Win32 function failure representation.
 */
public interface ResultValue {
    public fun failure(): Boolean

    public companion object {
        public const val NULL: Long = 0L
    }
}
