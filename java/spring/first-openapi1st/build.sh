#!/usr/bin/env bash
#
# build.sh — Full rebuild of first-openapi1st project.
#
# Order:  api → client → service → web
#   api      : standalone, bundles spec + generates server interfaces & models
#   client   : standalone aggregator, depends on api spec for client SDK generation
#   service  : depends on api jar
#   web      : depends on api + service jars
#
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
M2_REPO="${HOME}/.m2/repository/com/github/btpka3"

echo "============================================================"
echo " first-openapi1st  full rebuild"
echo " $(date '+%Y-%m-%d %H:%M:%S')"
echo "============================================================"

# ------------------------------------------------------------------
# Step 0: Purge local Maven repository entries
# ------------------------------------------------------------------
echo ""
echo ">>> [0/4] Purging local Maven repository artifacts ..."

ARTIFACTS=(
    first-openapi1st-api
    first-openapi1st-client
    first-openapi1st-client-api
    first-openapi1st-client-impl-native
    first-openapi1st-service
    first-openapi1st-web
)

for artifact in "${ARTIFACTS[@]}"; do
    dir="${M2_REPO}/${artifact}"
    if [[ -d "$dir" ]]; then
        rm -rf "$dir"
        echo "    removed  ${dir}"
    else
        echo "    skip     ${dir} (not found)"
    fi
done
echo "    done."

# ------------------------------------------------------------------
# Step 1: first-openapi1st-api
# ------------------------------------------------------------------
echo ""
echo ">>> [1/4] Building first-openapi1st-api ..."
mvn -f "${SCRIPT_DIR}/first-openapi1st-api/pom.xml" clean install
echo "    first-openapi1st-api  ✓"

# ------------------------------------------------------------------
# Step 2: first-openapi1st-client  (aggregator: client-api + client-impl-native)
# ------------------------------------------------------------------
echo ""
echo ">>> [2/4] Building first-openapi1st-client ..."
mvn -f "${SCRIPT_DIR}/first-openapi1st-client/pom.xml" clean install
echo "    first-openapi1st-client  ✓"

# ------------------------------------------------------------------
# Step 3: first-openapi1st  (parent reactor: service + web)
# ------------------------------------------------------------------
echo ""
echo ">>> [3/4] Building first-openapi1st-service ..."
mvn -f "${SCRIPT_DIR}/first-openapi1st-service/pom.xml" clean install
echo "    first-openapi1st-service  ✓"

echo ""
echo ">>> [4/4] Building first-openapi1st-web ..."
mvn -f "${SCRIPT_DIR}/first-openapi1st-web/pom.xml" clean install
echo "    first-openapi1st-web  ✓"

# ------------------------------------------------------------------
echo ""
echo "============================================================"
echo " All modules built successfully."
echo " $(date '+%Y-%m-%d %H:%M:%S')"
echo "============================================================"
