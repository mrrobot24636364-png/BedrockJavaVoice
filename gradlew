#!/bin/sh
set -e
if command -v gradle >/dev/null 2>&1; then
  exec gradle "$@"
fi
echo "Gradle is not installed. Install Gradle 8.13+ or generate a standard Gradle wrapper with: gradle wrapper"
exit 127
