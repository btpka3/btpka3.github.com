#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
ROOT_DIR="$(dirname "$SCRIPT_DIR")"

echo "=== Bundling OpenAPI spec ==="
cd "$ROOT_DIR"

# Bundle all $ref into a single file
npx @redocly/cli bundle openapi-full.yaml -o dist/openapi-full.yaml

echo "=== Linting ==="
npx @redocly/cli lint dist/openapi-full.yaml --config bundle.config.yaml

echo "=== Done: dist/openapi-full.yaml ==="
