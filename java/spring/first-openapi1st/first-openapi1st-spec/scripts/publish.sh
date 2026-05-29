#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

cd "$ROOT_DIR"

echo "=== Step 1: Bundle ==="
bash scripts/bundle.sh

echo "=== Step 2: Validate ==="
bash scripts/validate.sh || true

echo "=== Step 3: Deploy to Maven ==="
# CI 中根据分支名自动设置版本号：
#   main       → 正式版 (如 1.2.0)
#   feature/*  → SNAPSHOT (如 1.3.0-add-pet-photos-SNAPSHOT)
#   release/*  → RC (如 1.3.0-RC1)
#
# 示例：通过 Maven versions plugin 设置版本
# mvn versions:set -DnewVersion=${SPEC_VERSION}

mvn deploy -DskipTests
echo "=== Published: $(mvn help:evaluate -Dexpression=project.version -q -DforceStdout) ==="
