// port-lint: source common.rs
package io.github.kotlinmania.arboard

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class CommonTest {
    @Test
    fun errorMessagesFollowUpstreamDisplayText() {
        assertEquals(
            "The clipboard contents were not available in the requested format or the clipboard is empty.",
            Error.ContentNotAvailable.message,
        )
        assertEquals(
            "ContentNotAvailable - \"The clipboard contents were not available in the requested format or the clipboard is empty.\"",
            Error.ContentNotAvailable.toString(),
        )

        val unknown = Error.Unknown("backend refused access")
        assertEquals(
            "Unknown error while interacting with the clipboard: backend refused access",
            unknown.message,
        )
        assertEquals(
            "Unknown { .. } - \"Unknown error while interacting with the clipboard: backend refused access\"",
            unknown.toString(),
        )
    }

    @Test
    fun unknownErrorsCompareByDetails() {
        assertEquals(Error.Unknown("same"), Error.Unknown("same"))
        assertNotEquals(Error.Unknown("same"), Error.Unknown("different"))
    }

    @Test
    fun imageDataOwnershipHelpersCopyBytes() {
        val bytes = byteArrayOf(255.toByte(), 0, 0, 255.toByte())
        val image = ImageData(width = 1, height = 1, bytes = bytes)

        val ownedBytes = image.intoOwnedBytes()
        assertContentEquals(bytes, ownedBytes)
        ownedBytes[0] = 0
        assertEquals(255.toByte(), image.bytes[0])

        val ownedImage = image.toOwnedImg()
        assertEquals(image.width, ownedImage.width)
        assertEquals(image.height, ownedImage.height)
        assertContentEquals(image.bytes, ownedImage.bytes)
        ownedImage.bytes[3] = 0
        assertEquals(255.toByte(), image.bytes[3])
    }

    @Test
    fun scopeGuardRunsCallbackOnce() {
        var calls = 0
        val guard = ScopeGuard.new { calls += 1 }

        guard.close()
        guard.close()

        assertEquals(1, calls)
    }
}
