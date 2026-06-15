---
name: nether-ore-target-tag
description: MC 1.20.1 Forge — no minecraft:netherrack block tag exists; nether ore targets need block_match or base_stone_nether
metadata:
  type: reference
---

In Minecraft 1.20.1 worldgen ore features, there is **no `minecraft:netherrack` block tag** — `minecraft:netherrack` is only a block id. To target nether stone in an `minecraft:ore` configured feature, use either:
- `{ "predicate_type": "minecraft:block_match", "block": "minecraft:netherrack" }` (vanilla quartz/gold ore approach), or
- `{ "predicate_type": "minecraft:tag_match", "tag": "minecraft:base_stone_nether" }` (broader: netherrack + basalt + blackstone).

A `tag_match` pointing at a non-existent tag silently resolves to an empty set (no error), so the ore replaces nothing and `/place feature` reports "Failed to place feature" even when standing on netherrack. `block_match` uses the `block` key; `tag_match` uses the `tag` key — don't mix them up. Overworld equivalents: `minecraft:stone_ore_replaceables` / `minecraft:deepslate_ore_replaceables`. Verified against vanilla data in client-extra.jar.
