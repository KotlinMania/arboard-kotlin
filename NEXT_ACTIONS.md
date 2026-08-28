# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 8/8 (100.0%)
- **Function parity:** 95/126 matched (target 158) — 75.4%
- **Class/type parity:** 22/41 matched (target 49) — 53.7%
- **Combined symbol parity:** 117/167 matched (target 207) — 70.1%
- **Average inline-code cosine:** 0.29 (function body across 6 matched files)
- **Average documentation cosine:** 0.49 (doc text across 6 matched files)
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 8 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. platform.windows

- **Target:** `platform.Windows [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 264010.0
- **Functions:** 10/32 matched (target 15)
- **Missing functions:** `add_cf_dibv5`, `add_png_file`, `maybe_tweak_header`, `read_cf_dibv5`, `read_png`, `rgba_to_win`, `flip_v`, `win_to_rgba`, `convert_bytes_to_u32s`, `global_alloc`, `global_lock`, `global_unlock_checked`, `last_error`, `failure`, `drop`, `add_clipboard_exclusions`, `exclude_from_monitoring`, `exclude_from_cloud`, `exclude_from_history`, `wrap_html`, `to_final_path_wide`, `fill_utf16_buf`
- **Types:** 4/8 matched
- **Missing types:** `Clipboard`, `Get`, `Set`, `Clear`
- **Tests:** 3/4 matched

### 2. platform.osx

- **Target:** `platform.Osx`
- **Similarity:** 0.11
- **Dependents:** 0
- **Priority Score:** 81608.9
- **Functions:** 7/11 matched (target 12)
- **Missing functions:** `image_from_pixels`, `release`, `add_clipboard_exclusions`, `exclude_from_history`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `Clipboard`, `Get`, `Set`, `Clear`

### 3. linux.mod

- **Target:** `linux.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 72510.0
- **Functions:** 14/16 matched (target 21)
- **Missing functions:** `into_unknown`, `encode_as_png`
- **Types:** 4/9 matched (target 10)
- **Missing types:** `LinuxClipboardKind`, `Clipboard`, `Get`, `Set`, `Clear`
- **Tests:** 1/1 matched

### 4. linux.x11

- **Target:** `linux.X11`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 63607.5
- **Functions:** 25/27 matched (target 30)
- **Missing functions:** `serve_requests`, `handover_finished`
- **Types:** 5/9 matched (target 7)
- **Missing types:** `GlobalClipboard`, `XContext`, `Inner`, `Clipboard`
- **Lint issues:** 3

### 5. linux.wayland

- **Target:** `linux.Wayland`
- **Similarity:** 0.35
- **Dependents:** 0
- **Priority Score:** 31706.5
- **Functions:** 14/15 matched (target 20)
- **Missing functions:** `handle_clipboard_read`
- **Types:** 0/2 matched
- **Missing types:** `Clipboard`, `Error`
- **Lint issues:** 1

### 6. lib

- **Target:** `arboard.Lib`
- **Similarity:** 0.55
- **Dependents:** 0
- **Priority Score:** 2304.5
- **Functions:** 19/19 matched (target 42)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 7)
- **Missing types:** _none_
- **Tests:** 4/4 matched

### 7. common

- **Target:** `arboard.Common`
- **Similarity:** 0.50
- **Dependents:** 0
- **Priority Score:** 1005.0
- **Functions:** 6/6 matched (target 17)
- **Missing functions:** _none_
- **Types:** 4/4 matched (target 11)
- **Missing types:** _none_

### 8. platform.mod

- **Target:** `platform.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10.0
- **Functions:** 0/0 matched (target 1)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 2)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

