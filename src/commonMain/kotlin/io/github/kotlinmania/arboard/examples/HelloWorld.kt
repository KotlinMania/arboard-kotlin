// port-lint: source examples/hello_world.rs
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
 * Hello world example demonstrating basic text reading and writing to the clipboard.
 */
public object HelloWorld {
    @JvmStatic
    public fun main(args: Array<String>) {
        val clipboard = Clipboard.new()
        println("Clipboard text was: ${clipboard.getText()}")

        val theString = "Hello, world!"
        clipboard.setText(theString)
        println("But now the clipboard text should be: \"$theString\"")
    }
}
