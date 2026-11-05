#!/usr/bin/env bash
# Read-only release gate: validate the actual signed bytes before publishing.
set -euo pipefail

apk="${1:-app/build/outputs/apk/release/app-release.apk}"
sdk="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-}}"
expected_certificate="94dcfab4eb5d2046605f1699f5bf99a9918bbf5eac78f25cc12effe4bdc95a6d"
expected_version=$(sed -n 's/.*versionName = "\([^"]*\)".*/\1/p' app/build.gradle.kts | head -n 1)
expected_code=$(sed -n 's/.*versionCode = \([0-9]*\).*/\1/p' app/build.gradle.kts | head -n 1)

test -s "$apk"
test -n "$expected_version"
test -n "$expected_code"
test -d "$sdk/build-tools"
build_tools=$(find "$sdk/build-tools" -mindepth 1 -maxdepth 1 -type d | sort -V | tail -n 1)
test -x "$build_tools/apksigner"
test -x "$build_tools/aapt"

signature=$("$build_tools/apksigner" verify --verbose --print-certs "$apk")
certificate=$(printf '%s\n' "$signature" | sed -n 's/^Signer #1 certificate SHA-256 digest: //p')
if [[ "$certificate" != "$expected_certificate" ]]; then
  echo "Release signing certificate does not match the installed-release baseline." >&2
  exit 1
fi
[[ "$signature" == *"Verified using v2 scheme (APK Signature Scheme v2): true"* ]]
[[ "$signature" == *"Verified using v3 scheme (APK Signature Scheme v3): true"* ]]

badging=$("$build_tools/aapt" dump badging "$apk")
package_line="${badging%%$'\n'*}"
[[ "$package_line" == *"name='com.personalieltscoach' versionCode='$expected_code' versionName='$expected_version'"* ]]

echo "APK_VERIFIED=true"
echo "APK_VERSION=$expected_version"
echo "APK_VERSION_CODE=$expected_code"
echo "APK_CERTIFICATE_SHA256=$certificate"
echo "APK_SHA256=$(sha256sum "$apk" | cut -d ' ' -f 1)"
echo "APK_SIZE=$(stat -c %s "$apk")"
