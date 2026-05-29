#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

echo "=== Breaking change detection ==="
cd "$ROOT_DIR"

# Compare current branch spec against main branch
# Requires: npm install -g @openapitools/openapi-diff

if git show main:dist/openapi-full.yaml > /tmp/openapi-main.yaml 2>/dev/null; then
    npx openapi-diff /tmp/openapi-main.yaml dist/openapi-full.yaml || {
        echo "WARNING: Breaking changes detected! Review carefully."
        exit 1
    }
    echo "No breaking changes detected."
else
    echo "SKIP: No main branch spec found (first time build)."
fi
