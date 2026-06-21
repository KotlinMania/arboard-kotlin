// port-lint: source common.rs
/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard

/**
 * An error that might happen during a clipboard operation.
 *
 * Note that both the `Throwable.message` and the `toString()` representation are implemented for
 * this type in such a way that they give a short human-readable description of the error;
 * however the documentation gives a more detailed explanation for each error kind.
 *
 * The upstream type carries a non-exhaustive marker, indicating that new variants may be
 * added in future releases.
 */
sealed class Error(
    message: String,
) : Throwable(message) {
    /** The clipboard contents were not available in the requested format.
     *  This could either be due to the clipboard being empty or the clipboard contents having
     *  an incompatible format to the requested one (eg when calling `getImage` on text) */
    object ContentNotAvailable : Error(
        "The clipboard contents were not available in the requested format or the clipboard is empty.",
    )

    /** The selected clipboard is not supported by the current configuration (system and/or environment).
     *
     *  This can be caused by a few conditions:
     *  - Using the Primary clipboard with an older Wayland compositor (that doesn't support version 2)
     *  - Using the Secondary clipboard on Wayland */
    object ClipboardNotSupported : Error(
        "The selected clipboard is not supported with the current system configuration.",
    )

    /** The native clipboard is not accessible due to being held by another party.
     *
     *  This "other party" could be a different process or it could be within
     *  the same program. So for example you may get this error when trying
     *  to interact with the clipboard from multiple threads at once.
     *
     *  Note that it's OK to have multiple `Clipboard` instances. The underlying
     *  implementation will make sure that the native clipboard is only
     *  opened for transferring data and then closed as soon as possible. */
    object ClipboardOccupied : Error(
        "The native clipboard is not accessible due to being held by another party.",
    )

    /** The image or the text that was about the be transferred to/from the clipboard could not be
     *  converted to the appropriate format. */
    object ConversionFailure : Error(
        "The image or the text that was about the be transferred to/from the clipboard could not be converted to the appropriate format.",
    )

    /** Any error that doesn't fit the other error types.
     *
     *  The `description` field is only meant to help the developer and should not be relied on as a
     *  means to identify an error case during runtime. */
    class Unknown(
        val description: String,
    ) : Error(
            "Unknown error while interacting with the clipboard: $description",
        ) {
        override fun equals(other: Any?): Boolean =
            this === other || (other is Unknown && other.description == description)

        override fun hashCode(): Int = description.hashCode()
    }

    override fun toString(): String {
        val name =
            when (this) {
                ContentNotAvailable -> "ContentNotAvailable"
                ClipboardNotSupported -> "ClipboardNotSupported"
                ClipboardOccupied -> "ClipboardOccupied"
                ConversionFailure -> "ConversionFailure"
                is Unknown -> "Unknown { .. }"
            }
        return "$name - \"${message ?: ""}\""
    }

    internal companion object {
        internal fun unknown(message: String): Error = Unknown(message)
    }
}

/**
 * Stores pixel data of an image.
 *
 * Each element in `bytes` stores the value of a channel of a single pixel.
 * This struct stores four channels (red, green, blue, alpha) so
 * a `3*3` image is going to be stored on `3*3*4 = 36` bytes of data.
 *
 * The pixels are in row-major order meaning that the second pixel
 * in `bytes` (starting at the fifth byte) corresponds to the pixel that's
 * sitting to the right side of the top-left pixel (x=1, y=0)
 *
 * Assigning a `2*1` image would for example look like this
 * ```
 * import io.github.kotlinmania.arboard.ImageData
 * val bytes = byteArrayOf(
 *     // A red pixel
 *     255.toByte(), 0, 0, 255.toByte(),
 *
 *     // A green pixel
 *     0, 255.toByte(), 0, 255.toByte(),
 * )
 * val img = ImageData(
 *     width = 2,
 *     height = 1,
 *     bytes = bytes,
 * )
 * ```
 *
 * Gated upstream behind the `image-data` Cargo feature.
 */
class ImageData(
    val width: Int,
    val height: Int,
    val bytes: ByteArray,
) {
    /** Returns a the bytes field in a way that it's guaranteed to be owned.
     *  It moves the bytes if they are already owned and clones them if they are borrowed. */
    fun intoOwnedBytes(): ByteArray = bytes.copyOf()

    /** Returns an image data that is guaranteed to own its bytes.
     *  It moves the bytes if they are already owned and clones them if they are borrowed. */
    fun toOwnedImg(): ImageData =
        ImageData(
            width = width,
            height = height,
            bytes = bytes.copyOf(),
        )

    override fun equals(other: Any?): Boolean =
        this === other ||
            (
                other is ImageData &&
                    other.width == width &&
                    other.height == height &&
                    other.bytes.contentEquals(bytes)
            )

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        result = 31 * result + bytes.contentHashCode()
        return result
    }

    override fun toString(): String =
        "ImageData(width=$width, height=$height, bytes=[${bytes.size} bytes])"
}

/**
 * Runs a callback when this guard is closed. Upstream models this as an RAII type that fires the
 * callback in its `Drop` impl; Kotlin has no destructors, so the type implements `AutoCloseable`
 * and callers must invoke it via `.use { … }` or an explicit `close()`.
 *
 * Gated upstream to `windows` and `unix` (non-macOS) configurations.
 */
internal class ScopeGuard(
    callback: () -> Unit,
) : AutoCloseable {
    private var callback: (() -> Unit)? = callback

    override fun close() {
        val cb = callback
        if (cb != null) {
            callback = null
            cb()
        }
    }

    internal companion object {
        internal fun new(callback: () -> Unit): ScopeGuard = ScopeGuard(callback)
    }
}

/** Common interface for sealing platform extension types. */
internal object Private {
    interface Sealed

    // Upstream additionally implements `Sealed` for the `Get`, `Set`, and `Clear` builders
    // declared in `lib.rs`. Those types will declare `Private.Sealed` as a supertype when
    // their own port lands.
}
