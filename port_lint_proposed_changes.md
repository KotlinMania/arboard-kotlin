# port-lint Proposed Changes

**Generated:** 2026-08-31
**Source:** tmp/arboard/src
**Target:** src/commonMain/kotlin/io/github/kotlinmania/arboard

These are review proposals only. They are emitted when a Rust -> Kotlin pair matches only after fallback normalization, so the existing `port-lint` header is not an exact provenance match.

| Target file | Current header | Proposed header | Source path | Reason |
|-------------|----------------|-----------------|-------------|--------|
| `src/commonMain/kotlin/io/github/kotlinmania/arboard/platform/Windows.kt` | `// port-lint: source arboard/src/platform/windows.rs` | `// port-lint: source platform/windows.rs` | `platform/windows.rs` | `port-lint provenance header matched only after fallback normalization: 'arboard/src/platform/windows.rs' vs expected 'platform/windows.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/arboard/platform/WindowsTest.kt` | `// port-lint: tests arboard/src/platform/windows.rs` | `// port-lint: tests platform/windows.rs` | `platform/windows.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:arboard/src/platform/windows.rs' vs expected 'platform/windows.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/arboard/platform/linux/Mod.kt` | `// port-lint: source arboard/src/platform/linux/mod.rs` | `// port-lint: source platform/linux/mod.rs` | `platform/linux/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'arboard/src/platform/linux/mod.rs' vs expected 'platform/linux/mod.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/arboard/platform/LinuxTest.kt` | `// port-lint: tests arboard/src/platform/linux/mod.rs` | `// port-lint: tests platform/linux/mod.rs` | `platform/linux/mod.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:arboard/src/platform/linux/mod.rs' vs expected 'platform/linux/mod.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/arboard/platform/Osx.kt` | `// port-lint: source arboard/src/platform/osx.rs` | `// port-lint: source platform/osx.rs` | `platform/osx.rs` | `port-lint provenance header matched only after fallback normalization: 'arboard/src/platform/osx.rs' vs expected 'platform/osx.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/arboard/platform/linux/X11.kt` | `// port-lint: source arboard/src/platform/linux/x11.rs` | `// port-lint: source platform/linux/x11.rs` | `platform/linux/x11.rs` | `port-lint provenance header matched only after fallback normalization: 'arboard/src/platform/linux/x11.rs' vs expected 'platform/linux/x11.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/arboard/platform/linux/Wayland.kt` | `// port-lint: source arboard/src/platform/linux/wayland.rs` | `// port-lint: source platform/linux/wayland.rs` | `platform/linux/wayland.rs` | `port-lint provenance header matched only after fallback normalization: 'arboard/src/platform/linux/wayland.rs' vs expected 'platform/linux/wayland.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/arboard/Lib.kt` | `// port-lint: source arboard/src/lib.rs` | `// port-lint: source lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'arboard/src/lib.rs' vs expected 'lib.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/arboard/LibTest.kt` | `// port-lint: tests arboard/src/lib.rs` | `// port-lint: tests lib.rs` | `lib.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:arboard/src/lib.rs' vs expected 'lib.rs'` |
| `src/commonMain/kotlin/io/github/kotlinmania/arboard/Common.kt` | `// port-lint: source arboard/src/common.rs` | `// port-lint: source common.rs` | `common.rs` | `port-lint provenance header matched only after fallback normalization: 'arboard/src/common.rs' vs expected 'common.rs'` |
| `src/commonTest/kotlin/io/github/kotlinmania/arboard/CommonTest.kt` | `// port-lint: tests arboard/src/common.rs` | `// port-lint: tests common.rs` | `common.rs` | `port-lint provenance header matched only after fallback normalization: 'tests:arboard/src/common.rs' vs expected 'common.rs'` |
