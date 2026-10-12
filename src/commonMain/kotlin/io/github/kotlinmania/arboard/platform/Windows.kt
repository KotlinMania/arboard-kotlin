// port-lint: source platform/windows.rs
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
        public const val LCS_SRGB: UInt = 0x73524742u
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

        public const val LCS_sRGB: UInt = 0x73524742u

        public fun new(): WindowsClipboard = WindowsClipboard()

        public fun winToRgba(bytes: ByteArray): ByteArray = winToRgbaInPlace(bytes.copyOf())

        public fun rgbaToWin(bytes: ByteArray): ByteArray = rgbaToWinInPlace(bytes.copyOf())

        public fun winToRgbaInPlace(bytes: ByteArray): ByteArray {
            require(bytes.size % 4 == 0) { "Byte array length must be a multiple of 4" }
            for (i in bytes.indices step 4) {
                val b = bytes[i]
                val r = bytes[i + 2]
                bytes[i] = r
                bytes[i + 2] = b
            }
            return bytes
        }

        public fun rgbaToWinInPlace(bytes: ByteArray): ByteArray {
            require(bytes.size % 4 == 0) { "Byte array length must be a multiple of 4" }
            for (i in bytes.indices step 4) {
                val r = bytes[i]
                val b = bytes[i + 2]
                bytes[i] = b
                bytes[i + 2] = r
            }
            return bytes
        }

        public fun flipV(image: ImageData): ImageData {
            val w = image.width
            val h = image.height
            val bytes = image.bytes.copyOf()
            val rowSize = w * 4
            val tmp = ByteArray(rowSize)
            for (aRow in 0 until (h / 2)) {
                val bRow = h - aRow - 1
                val aStart = aRow * rowSize
                val bStart = bRow * rowSize
                bytes.copyInto(tmp, 0, aStart, aStart + rowSize)
                bytes.copyInto(bytes, aStart, bStart, bStart + rowSize)
                tmp.copyInto(bytes, bStart, 0, rowSize)
            }
            return ImageData(width = w, height = h, bytes = bytes)
        }

        public fun maybeTweakHeader(dibv5: ByteArray) {
            if (dibv5.size < 124) return
            val bitCount = (dibv5[14].toInt() and 0xFF) or ((dibv5[15].toInt() and 0xFF) shl 8)
            val compression =
                (dibv5[16].toInt() and 0xFF) or
                    ((dibv5[17].toInt() and 0xFF) shl 8) or
                    ((dibv5[18].toInt() and 0xFF) shl 16) or
                    ((dibv5[19].toInt() and 0xFF) shl 24)
            val alphaMask =
                (dibv5[52].toInt() and 0xFF) or
                    ((dibv5[53].toInt() and 0xFF) shl 8) or
                    ((dibv5[54].toInt() and 0xFF) shl 16) or
                    ((dibv5[55].toInt() and 0xFF) shl 24)

            if (bitCount == 32 && compression == 0 && alphaMask == 0xFF000000.toInt()) {
                dibv5[16] = 3
                dibv5[17] = 0
                dibv5[18] = 0
                dibv5[19] = 0

                val redMask =
                    (dibv5[40].toInt() and 0xFF) or
                        ((dibv5[41].toInt() and 0xFF) shl 8) or
                        ((dibv5[42].toInt() and 0xFF) shl 16) or
                        ((dibv5[43].toInt() and 0xFF) shl 24)
                val greenMask =
                    (dibv5[44].toInt() and 0xFF) or
                        ((dibv5[45].toInt() and 0xFF) shl 8) or
                        ((dibv5[46].toInt() and 0xFF) shl 16) or
                        ((dibv5[47].toInt() and 0xFF) shl 24)
                val blueMask =
                    (dibv5[48].toInt() and 0xFF) or
                        ((dibv5[49].toInt() and 0xFF) shl 8) or
                        ((dibv5[50].toInt() and 0xFF) shl 16) or
                        ((dibv5[51].toInt() and 0xFF) shl 24)

                if (redMask == 0 && greenMask == 0 && blueMask == 0) {
                    dibv5[40] = 0
                    dibv5[41] = 0
                    dibv5[42] = 255.toByte()
                    dibv5[43] = 0

                    dibv5[44] = 0
                    dibv5[45] = 255.toByte()
                    dibv5[46] = 0
                    dibv5[47] = 0

                    dibv5[48] = 255.toByte()
                    dibv5[49] = 0
                    dibv5[50] = 0
                    dibv5[51] = 0
                }
            }
        }

        public fun readCfDibv5(dibv5: ByteArray): ImageData {
            val headerSize = 124
            if (dibv5.size < headerSize) {
                throw Error.Unknown("When reading the DIBV5 data, it contained fewer bytes than the BITMAPV5HEADER size. This is invalid.")
            }
            maybeTweakHeader(dibv5)

            fun readInt32LE(offset: Int): Int =
                (dibv5[offset].toInt() and 0xFF) or
                    ((dibv5[offset + 1].toInt() and 0xFF) shl 8) or
                    ((dibv5[offset + 2].toInt() and 0xFF) shl 16) or
                    ((dibv5[offset + 3].toInt() and 0xFF) shl 24)

            fun readInt16LE(offset: Int): Int =
                (dibv5[offset].toInt() and 0xFF) or
                    ((dibv5[offset + 1].toInt() and 0xFF) shl 8)

            val width = readInt32LE(4)
            val rawHeight = readInt32LE(8)
            val isBottomUp = rawHeight > 0
            val height = if (isBottomUp) rawHeight else -rawHeight
            val bitCount = readInt16LE(14)

            if (bitCount != 24 && bitCount != 32) {
                throw Error.ConversionFailure
            }

            val rowSize =
                if (bitCount == 24) {
                    ((width * 3 + 3) / 4) * 4
                } else {
                    width * 4
                }

            val outputBytes = ByteArray(width * height * 4)

            for (y in 0 until height) {
                val srcRow = if (isBottomUp) (height - 1 - y) else y
                val srcOffset = headerSize + srcRow * rowSize
                val dstOffset = y * width * 4

                for (x in 0 until width) {
                    val dstPixel = dstOffset + x * 4
                    if (bitCount == 24) {
                        val srcPixel = srcOffset + x * 3
                        val b = dibv5[srcPixel]
                        val g = dibv5[srcPixel + 1]
                        val r = dibv5[srcPixel + 2]
                        outputBytes[dstPixel] = r
                        outputBytes[dstPixel + 1] = g
                        outputBytes[dstPixel + 2] = b
                        outputBytes[dstPixel + 3] = 255.toByte()
                    } else {
                        val srcPixel = srcOffset + x * 4
                        val b = dibv5[srcPixel]
                        val g = dibv5[srcPixel + 1]
                        val r = dibv5[srcPixel + 2]
                        val a = dibv5[srcPixel + 3]
                        outputBytes[dstPixel] = r
                        outputBytes[dstPixel + 1] = g
                        outputBytes[dstPixel + 2] = b
                        outputBytes[dstPixel + 3] = a
                    }
                }
            }

            return ImageData(width = width, height = height, bytes = outputBytes)
        }
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
