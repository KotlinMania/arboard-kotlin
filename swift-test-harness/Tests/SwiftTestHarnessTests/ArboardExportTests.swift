import Testing
import Arboard

@Suite("Arboard Swift Export Suite")
struct ArboardExportTests {
    @Test("Swift module loads cleanly and basic Clipboard functions")
    func swiftModuleLoads() throws {
        let clipboard = Clipboard.Companion.shared.create()
        clipboard.setText(text: "swift export test")
        #expect(clipboard.getText() == "swift export test")
    }
}
