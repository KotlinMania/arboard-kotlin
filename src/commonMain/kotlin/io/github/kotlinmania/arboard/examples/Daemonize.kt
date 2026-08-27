// port-lint: source arboard/examples/daemonize.rs
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
 * Example demonstrating daemonized clipboard operation.
 */
public object Daemonize {
    public const val DAEMONIZE_ARG: String = "__internal_daemonize"

    @JvmStatic
    public fun main(args: Array<String>) {
        val clipboard = Clipboard.new()
        clipboard.set().wait().text("Hello, world!")
    }
}
