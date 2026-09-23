# COH / JDK 27 regression investigation notes

Branch: `cursor/coh-perf-investigation-6168`, based on upstream/main `9f5fe0e5a22` (2026-09-23).
JDK bump under suspicion: `9446dc8fad8 Bump bundled JDK to 27 (#159368)`, 26.0.2+10 -> 27+35.

STATUS: step 1 done (machine specs recorded)

## Step 1: machine

- `lscpu`: x86_64, Intel Xeon (Sapphire Rapids, family 6 model 207 stepping 2), 8 vCPU, 1 thread/core,
  KVM guest, AVX-512 + AMX, L1d 48K/core, L2 2M/core, L3 320M shared, 1 NUMA node.
- `nproc`: 8
- `free -g`: 47 total, 21 available, no swap
- `df -h /`: 254G size, 245G avail -> full corpus (no `ingest_percentage`)
- Note: this is x86_64, NOT the nightly's Graviton2 / Neoverse N1. Architecture-dependent findings
  (hypothesis 2) apply to x86 only.

## Commands

```
lscpu; nproc; free -g; df -h
git remote add upstream https://github.com/elastic/elasticsearch.git && git fetch upstream main
git checkout -B cursor/coh-perf-investigation-6168 upstream/main
```
