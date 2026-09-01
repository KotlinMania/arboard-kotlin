// port-lint: source ../examples/set_image.rs
/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.examples

import io.github.kotlinmania.arboard.Clipboard
import io.github.kotlinmania.arboard.ImageData
import kotlin.jvm.JvmStatic

/**
 * Example demonstrating setting image data on the clipboard.
 */
public object SetImage {
    @JvmStatic
    public fun main(args: Array<String>) {
        val ctx = Clipboard.new()
        val bytes =
            byteArrayOf(
                255.toByte(),
                100,
                100,
                255.toByte(),
                100,
                255.toByte(),
                100,
                100,
                100,
                100,
                255.toByte(),
                100,
                0,
                0,
                0,
                255.toByte(),
            )
        val imgData = ImageData(width = 2, height = 2, bytes = bytes)
        ctx.setImage(imgData)
    }
}
