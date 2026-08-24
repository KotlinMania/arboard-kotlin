# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 8/8 (100.0%)
- **Function parity:** 57/126 matched (target 118) — 45.2%
- **Class/type parity:** 13/41 matched (target 32) — 31.7%
- **Combined symbol parity:** 70/167 matched (target 150) — 41.9%
- **Average inline-code cosine:** 0.23 (function body across 6 matched files)
- **Average documentation cosine:** 0.45 (doc text across 6 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 8 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. platform.windows

- **Target:** `platform.Windows`
- **Similarity:** 0.06
- **Dependents:** 0
- **Priority Score:** 334009.4
- **Functions:** 6/32 matched (target 11)
- **Missing functions:** `add_cf_dibv5`, `add_png_file`, `maybe_tweak_header`, `read_cf_dibv5`, `read_png`, `rgba_to_win`, `flip_v`, `win_to_rgba`, `convert_bytes_to_u32s`, `conversion_between_win_and_rgba`, `firefox_dibv5`, `chrome_dibv5`, `global_alloc`, `global_lock`, `global_unlock_checked`, `last_error`, `failure`, `drop`, `open`, `add_clipboard_exclusions`, `exclude_from_monitoring`, `exclude_from_cloud`, `exclude_from_history`, `wrap_html`, `to_final_path_wide`, `fill_utf16_buf`
- **Types:** 1/8 matched (target 2)
- **Missing types:** `ImageDataCow`, `ResultValue`, `Clipboard`, `OpenClipboard`, `Get`, `Set`, `Clear`
- **Tests:** 0/4 matched

### 2. linux.x11

- **Target:** `linux.X11`
- **Similarity:** 0.06
- **Dependents:** 0
- **Priority Score:** 303609.4
- **Functions:** 6/27 matched (target 11)
- **Missing functions:** `write`, `read`, `read_single`, `atom_of`, `selection_of`, `kind_of`, `is_owner`, `atom_name`, `atom_name_dbg`, `handle_read_selection_notify`, `handle_read_property_notify`, `handle_selection_request`, `ask_clipboard_manager_to_request_our_data`, `serve_requests`, `handover_finished`, `add_clipboard_exclusions`, `get_text`, `get_html`, `get_image`, `get_file_list`, `drop`
- **Types:** 0/9 matched (target 2)
- **Missing types:** `Result`, `ManagerHandoverState`, `GlobalClipboard`, `XContext`, `Inner`, `Selection`, `ClipboardData`, `ReadSelNotifyResult`, `Clipboard`

### 3. linux.mod

- **Target:** `linux.Mod [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 142510.0
- **Functions:** 8/16 matched (target 15)
- **Missing functions:** `into_unknown`, `encode_as_png`, `paths_from_uri_list`, `paths_to_uri_list`, `wait_until`, `exclude_from_history`, `clear_inner`, `test_decoding_uri_list`
- **Types:** 3/9 matched (target 5)
- **Missing types:** `LinuxClipboardKind`, `Clipboard`, `Get`, `WaitConfig`, `Set`, `Clear`
- **Tests:** 0/1 matched

### 4. linux.wayland

- **Target:** `linux.Wayland`
- **Similarity:** 0.11
- **Dependents:** 0
- **Priority Score:** 111708.9
- **Functions:** 6/15 matched (target 11)
- **Missing functions:** `try_into`, `add_clipboard_exclusions`, `handle_copy_error`, `handle_paste_error`, `handle_clipboard_read`, `get_text`, `get_html`, `get_image`, `get_file_list`
- **Types:** 0/2 matched
- **Missing types:** `Clipboard`, `Error`

### 5. platform.osx

- **Target:** `platform.Osx`
- **Similarity:** 0.09
- **Dependents:** 0
- **Priority Score:** 91609.1
- **Functions:** 6/11 matched
- **Missing functions:** `image_from_pixels`, `release`, `string_from_type`, `add_clipboard_exclusions`, `exclude_from_history`
- **Types:** 1/5 matched (target 2)
- **Missing types:** `Clipboard`, `Get`, `Set`, `Clear`

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
- **Functions:** 0/0 matched
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 1)
- **Missing types:** _none_

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

