/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.platform.linux

/**
 * The kind of Linux clipboard to interact with.
 *
 * Linux has a concept of clipboard "selections" which tend to be used in different contexts. This
 * enum provides a way to get/set to a specific clipboard (the default
 * `Clipboard` being used for the common platform API).
 */
public enum class LinuxClipboardKind {
    /**
     * Typically used selection for explicit cut/copy/paste actions (ie. windows/macos like
     * clipboard behavior).
     */
    Clipboard,

    /**
     * Typically used for mouse selections and/or currently selected text. Accessible via middle
     * mouse click.
     */
    Primary,

    /**
     * The secondary clipboard is rarely used but theoretically available on X11.
     */
    Secondary,
}
