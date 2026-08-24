import XCTest
import Arboard

final class ArboardExportTests: XCTestCase {
    func testSwiftModuleLoads() throws {
        let clipboard = Clipboard.Companion.shared.create()
        clipboard.setText(text: "swift export test")
        XCTAssertEqual(clipboard.getText(), "swift export test")
    }
}
