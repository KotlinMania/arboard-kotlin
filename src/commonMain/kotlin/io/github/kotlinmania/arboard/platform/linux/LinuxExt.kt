/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.platform.linux

/**
 * Linux-specific extensions to the [io.github.kotlinmania.arboard.Get] builder.
 */
public interface GetExtLinux {
    /**
     * Sets which Linux clipboard to read from.
     */
    public fun clipboard(selection: LinuxClipboardKind): GetExtLinux
}

/**
 * Configuration on how long to wait for a new X11 copy event.
 */
public sealed class WaitConfig {
    public class Until(
        public val deadlineMillis: Long,
    ) : WaitConfig()

    public object Forever : WaitConfig()

    public object None : WaitConfig()
}

/**
 * Linux-specific extensions to the [io.github.kotlinmania.arboard.Set] builder.
 */
public interface SetExtLinux {
    /**
     * Sets which Linux clipboard to write to.
     */
    public fun clipboard(selection: LinuxClipboardKind): SetExtLinux

    /**
     * Completes the set operation and waits for a paste event if required.
     */
    public fun wait(): SetExtLinux

    /**
     * Waits until the given deadline in milliseconds.
     */
    public fun waitUntil(deadlineMillis: Long): SetExtLinux

    /**
     * Excludes data from clipboard manager history.
     */
    public fun excludeFromHistory(): SetExtLinux
}

/**
 * Linux-specific extensions to the [io.github.kotlinmania.arboard.Clear] builder.
 */
public interface ClearExtLinux {
    /**
     * Sets which Linux clipboard to clear.
     */
    public fun clipboard(selection: LinuxClipboardKind): ClearExtLinux
}
