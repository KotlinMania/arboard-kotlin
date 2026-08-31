// port-lint: source examples/get_image.rs
/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.examples

import io.github.kotlinmania.arboard.Clipboard
import kotlin.jvm.JvmStatic

/**
 * Example demonstrating reading image data from the clipboard.
 */
public object GetImage {
    @JvmStatic
    public fun main(args: Array<String>) {
        val ctx = Clipboard.new()
        val img = ctx.getImage()
        println("Image data is:\n${img.bytes.contentToString()}")
    }
}
