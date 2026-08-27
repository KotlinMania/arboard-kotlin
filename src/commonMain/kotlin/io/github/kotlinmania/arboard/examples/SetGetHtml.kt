// port-lint: source arboard/examples/set_get_html.rs
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
 * Example demonstrating HTML clipboard operations.
 */
public object SetGetHtml {
    @JvmStatic
    public fun main(args: Array<String>) {
        val ctx = Clipboard.new()

        val html =
            """
            <h1>Hello, World!</h1>
            <b>Lorem ipsum</b> dolor sit amet,<br>
            <i>consectetur adipiscing elit</i>.
            """.trimIndent()

        val altText =
            """
            Hello, World!
            Lorem ipsum dolor sit amet,
            consectetur adipiscing elit.
            """.trimIndent()

        ctx.setHtml(html, altText)

        val success = ctx.get().html() == html
        println("Set and Get html operations were successful: $success")
    }
}
