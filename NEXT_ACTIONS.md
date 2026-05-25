# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 2/8 (25.0%)
- **Function parity:** 4/111 matched (target 15) — 3.6%
- **Class/type parity:** 4/28 matched (target 12) — 14.3%
- **Combined symbol parity:** 8/139 matched (target 27) — 5.8%
- **Average inline-code cosine:** 0.45 (function body across 1 matched files)
- **Average documentation cosine:** 0.93 (doc text across 1 matched files)
- **Cheat-zeroed Files:** 1
- **Critical Issues:** 2 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. common

- **Target:** `arboard.Common`
- **Similarity:** 0.45
- **Dependents:** 0
- **Priority Score:** 21005.5
- **Functions:** 4/6 matched (target 15)
- **Missing functions:** `fmt`, `drop`
- **Types:** 4/4 matched (target 11)
- **Missing types:** _none_

### 2. platform.mod

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

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |
| `linux.mod` | `platform.linux.Mod` | 0 | `platform/linux/mod.rs` | `platform/linux/Mod.kt` |

