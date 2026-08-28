# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 13/13 (100.0%)
- **Function parity:** 100/131 matched (target 163) — 76.3%
- **Class/type parity:** 22/41 matched (target 54) — 53.7%
- **Combined symbol parity:** 122/172 matched (target 217) — 70.9%
- **Average inline-code cosine:** 0.48 (function body across 11 matched files)
- **Average documentation cosine:** 0.29 (doc text across 11 matched files)
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 9 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. platform.windows

- **Target:** `platform.Windows [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 264010.0
- **Functions:** 10/32 matched (target 15)
- **Missing functions:** `add_cf_dibv5`, `add_png_file`, `maybe_tweak_header`, `read_cf_dibv5`, `read_png`, `rgba_to_win`, `flip_v`, `win_to_rgba`, `convert_bytes_to_u32s`, `global_alloc`, `global_lock`, `global_unlock_checked`, `last_error`, `failure`, `drop`, `add_clipboard_exclusions`, `exclude_from_monitoring`, `exclude_from_cloud`, `exclude_from_history`, `wrap_html`, `to_final_path_wide`, `fill_utf16_buf`
- **Types:** 4/8 matched
- **Missing types:** `Clipboard`, `Get`, `Set`, `Clear`
- **Tests:** 3/4 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `platform/windows.rs` vs expected `platform/windows.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:platform/windows.rs` vs expected `platform/windows.rs`
- **Proposed provenance header:** `// port-lint: source platform/windows.rs` (current: `// port-lint: source platform/windows.rs`)
- **Proposed provenance header:** `// port-lint: tests platform/windows.rs` (current: `// port-lint: tests platform/windows.rs`)
- **Lint issues:** 2

### 2. platform.osx

- **Target:** `platform.Osx [PROVENANCE-FALLBACK]`
- **Similarity:** 0.11
- **Dependents:** 0
- **Priority Score:** 81608.9
- **Functions:** 7/11 matched (target 12)
- **Missing functions:** `image_from_pixels`, `release`, `add_clipboard_exclusions`, `exclude_from_history`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `Clipboard`, `Get`, `Set`, `Clear`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `platform/osx.rs` vs expected `platform/osx.rs`
- **Proposed provenance header:** `// port-lint: source platform/osx.rs` (current: `// port-lint: source platform/osx.rs`)
- **Lint issues:** 1

### 3. linux.mod

- **Target:** `linux.Mod [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 72510.0
- **Functions:** 14/16 matched (target 21)
- **Missing functions:** `into_unknown`, `encode_as_png`
- **Types:** 4/9 matched (target 10)
- **Missing types:** `LinuxClipboardKind`, `Clipboard`, `Get`, `Set`, `Clear`
- **Tests:** 1/1 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `platform/linux/mod.rs` vs expected `platform/linux/mod.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:platform/linux/mod.rs` vs expected `platform/linux/mod.rs`
- **Proposed provenance header:** `// port-lint: source platform/linux/mod.rs` (current: `// port-lint: source platform/linux/mod.rs`)
- **Proposed provenance header:** `// port-lint: tests platform/linux/mod.rs` (current: `// port-lint: tests platform/linux/mod.rs`)
- **Lint issues:** 2

### 4. linux.x11

- **Target:** `linux.X11 [PROVENANCE-FALLBACK]`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 63607.5
- **Functions:** 25/27 matched (target 30)
- **Missing functions:** `serve_requests`, `handover_finished`
- **Types:** 5/9 matched (target 7)
- **Missing types:** `GlobalClipboard`, `XContext`, `Inner`, `Clipboard`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `platform/linux/x11.rs` vs expected `platform/linux/x11.rs`
- **Proposed provenance header:** `// port-lint: source platform/linux/x11.rs` (current: `// port-lint: source platform/linux/x11.rs`)
- **Lint issues:** 4

### 5. linux.wayland

- **Target:** `linux.Wayland [PROVENANCE-FALLBACK]`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 31706.5
- **Functions:** 14/15 matched (target 20)
- **Missing functions:** `handle_clipboard_read`
- **Types:** 0/2 matched
- **Missing types:** `Clipboard`, `Error`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `platform/linux/wayland.rs` vs expected `platform/linux/wayland.rs`
- **Proposed provenance header:** `// port-lint: source platform/linux/wayland.rs` (current: `// port-lint: source platform/linux/wayland.rs`)
- **Lint issues:** 2

### 6. arboard.lib

- **Target:** `arboard.Lib [PROVENANCE-FALLBACK]`
- **Similarity:** 0.55
- **Dependents:** 0
- **Priority Score:** 2304.5
- **Functions:** 19/19 matched (target 42)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 7)
- **Missing types:** _none_
- **Tests:** 4/4 matched
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `lib.rs` vs expected `lib.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source lib.rs`)
- **Proposed provenance header:** `// port-lint: tests lib.rs` (current: `// port-lint: tests lib.rs`)
- **Lint issues:** 2

### 7. arboard.common

- **Target:** `arboard.Common [PROVENANCE-FALLBACK]`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 1005.0
- **Functions:** 6/6 matched (target 17)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 11)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `common.rs` vs expected `common.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:common.rs` vs expected `common.rs`
- **Proposed provenance header:** `// port-lint: source common.rs` (current: `// port-lint: source common.rs`)
- **Proposed provenance header:** `// port-lint: tests common.rs` (current: `// port-lint: tests common.rs`)
- **Lint issues:** 2

### 8. examples.daemonize

- **Target:** `examples.Daemonize`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 105.0
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Lint issues:** 1

### 9. examples.set_image

- **Target:** `examples.SetImage`
- **Similarity:** 0.64
- **Dependents:** 0
- **Priority Score:** 103.6
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Lint issues:** 1

### 10. examples.set_get_html

- **Target:** `examples.SetGetHtml`
- **Similarity:** 0.79
- **Dependents:** 0
- **Priority Score:** 102.1
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Lint issues:** 1

### 11. examples.get_image

- **Target:** `examples.GetImage`
- **Similarity:** 0.79
- **Dependents:** 0
- **Priority Score:** 102.1
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Lint issues:** 1

### 12. examples.hello_world

- **Target:** `examples.HelloWorld`
- **Similarity:** 0.84
- **Dependents:** 0
- **Priority Score:** 101.6
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_
- **Lint issues:** 1

### 13. platform.mod

- **Target:** `platform.Mod [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `platform/mod.rs` vs expected `platform/mod.rs`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `tests:platform/mod.rs` vs expected `platform/mod.rs`
- **Proposed provenance header:** `// port-lint: source platform/mod.rs` (current: `// port-lint: source platform/mod.rs`)
- **Proposed provenance header:** `// port-lint: tests platform/mod.rs` (current: `// port-lint: tests platform/mod.rs`)
- **Lint issues:** 2

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

