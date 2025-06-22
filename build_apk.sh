#!/data/data/com.termux/files/usr/bin/bash

set -e

APP_NAME=WsTermuxTVRemote
PACKAGE=com.example.wstermuxtv
VERSION=1
BUILD_TOOLS=$HOME/android-sdk/build-tools/29.0.3
PLATFORM=$HOME/android-sdk/platforms/android-29/android.jar

mkdir -p out classes res/gen

echo "[1/5] Compiling Kotlin..."
kotlinc src/MainActivity.kt -cp libs/okhttp.jar -d classes

echo "[2/5] Creating DEX..."
d8 --output out classes

echo "[3/5] Creating APK..."
aapt package -f -M AndroidManifest.xml -I $PLATFORM -F out/app.unaligned.apk -S res -m -J res/gen

echo "[4/5] Adding DEX and libs..."
cd out
zip -u app.unaligned.apk classes.dex
cd ..

echo "[5/5] Signing APK..."
apksigner sign --ks keystore.jks --ks-pass pass:password --key-pass pass:password \
  --out out/ws-termux-tv-remote.apk out/app.unaligned.apk

echo "✅ APK built successfully: out/ws-termux-tv-remote.apk"