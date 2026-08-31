// port-lint: tests arboard/src/platform/mod.rs
package io.github.kotlinmania.arboard.platform

import kotlin.test.Test
import kotlin.test.assertEquals

class ModTest {
    @Test
    fun testPlatformMod() {
        assertEquals("platform", PlatformMod.MODULE_NAME)
        assertEquals("arboard", PlatformMod.CRATE_NAME)
    }
}
