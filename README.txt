# ws-termux-tv-remote

This project allows you to build an Android TV remote receiver APK from Termux.

## Requirements
- Termux
- Android SDK (installed via commandline tools)
- Java (`openjdk-17`)
- OkHttp jar file placed in libs/okhttp.jar
- A generated keystore (named keystore.jks with password: password)

## Steps
1. Place okhttp.jar in `libs/` folder.
2. Make `build_apk.sh` executable: `chmod +x build_apk.sh`
3. Run: `./build_apk.sh`
4. The final APK will be at `out/ws-termux-tv-remote.apk`