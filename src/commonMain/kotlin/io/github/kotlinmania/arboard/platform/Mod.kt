// port-lint: source src/platform/mod.rs
/*
SPDX-License-Identifier: Apache-2.0 OR MIT

Copyright 2022 The Arboard contributors

The project to which this file belongs is licensed under either of
the Apache 2.0 or the MIT license at the licensee's choice. The terms
and conditions of the chosen license apply to this file.
*/

package io.github.kotlinmania.arboard.platform

/*
 * Migration ledger for the upstream platform module aggregator.
 *
 * Upstream src/platform/mod.rs is a re-export aggregator with no implementation
 * of its own. Three cfg-gated wildcard re-exports surface the platform-specific
 * Clipboard / Get / Set / Clear types plus their per-platform extension traits
 * to consumers in lib.rs:
 *
 *     // pub use linux::*;       (target_family = "unix" and not in
 *     //                          macos / android / emscripten)
 *     // pub use windows::*;     (target_os = "windows")
 *     // pub use osx::*;         (target_os = "macos")
 *
 * Per workspace policy, re-export-only mod.rs files do not mint typealiases in
 * Kotlin. Each consumer should reference the platform-specific symbol at its
 * defining package (io.github.kotlinmania.arboard.platform.linux,
 * io.github.kotlinmania.arboard.platform.windows, or
 * io.github.kotlinmania.arboard.platform.osx) and let Kotlin's
 * expect/actual mechanism select the right implementation for the target.
 *
 * Callers migrated:
 *   (none — lib.rs (Clipboard / Get / Set / Clear builders) has not yet been
 *   ported and is the only intra-repo consumer of these re-exports.)
 */

internal object Mod
