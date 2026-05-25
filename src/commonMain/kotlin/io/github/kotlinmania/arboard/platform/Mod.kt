// port-lint: source platform/mod.rs
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
 * Upstream platform/mod.rs is a re-export aggregator with no implementation
 * of its own. Three configuration-gated wildcard exports surface the platform-specific
 * Clipboard / Get / Set / Clear types plus their per-platform extension traits
 * to consumers in lib.rs:
 *
 *     linux wildcard when the target is Unix and not macOS, Android, or Emscripten
 *     windows wildcard when the target is Windows
 *     osx wildcard when the target is macOS
 *
 * Per workspace policy, re-export-only mod.rs files do not mint typealiases in
 * Kotlin. Each consumer should reference the platform-specific symbol at its
 * defining package (io.github.kotlinmania.arboard.platform.linux,
 * io.github.kotlinmania.arboard.platform.windows, or
 * io.github.kotlinmania.arboard.platform.osx) and allow Kotlin's
 * expect/actual mechanism to select the right implementation for the target.
 *
 * Callers migrated:
 *   No callers have been migrated; lib.rs owns the intra-repo consumer path.
 */

internal object Mod
