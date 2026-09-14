#!/data/data/com.termux/files/usr/bin/bash

PKG_PATH="com/craftplay777/lemehostauto"
ROOT="$HOME/LemeHostAuto"
ANDROID_JAR="$HOME/android-29.jar"
SIGNER_JAR="$HOME/uber-apk-signer.jar"
DOWNLOADS="$HOME/storage/downloads"

cd "$ROOT" || exit 1

if [ ! -f "$ANDROID_JAR" ]; then
    echo "Descargando android.jar..."
    curl -L -o "$ANDROID_JAR" "https://github.com/Sable/android-platforms/raw/master/android-29/android.jar"
fi

if [ ! -f "$SIGNER_JAR" ]; then
    echo "Descargando uber-apk-signer..."
    curl -L -o "$SIGNER_JAR" "https://github.com/patrickfav/uber-apk-signer/releases/download/v1.3.0/uber-apk-signer-1.3.0.jar"
fi

rm -rf gen classes classes_dex compiled_res.zip app-unsigned.apk output
mkdir -p gen classes classes_dex output

aapt2 compile --dir res -o compiled_res.zip || { echo "Falló aapt2 compile"; exit 1; }

aapt2 link -o app-unsigned.apk \
    -I "$ANDROID_JAR" \
    --manifest AndroidManifest.xml \
    -R compiled_res.zip \
    --java gen \
    --auto-add-overlay || { echo "Falló aapt2 link"; exit 1; }

javac -source 8 -target 8 -bootclasspath "$ANDROID_JAR" -classpath "$ANDROID_JAR" -d classes \
    "gen/$PKG_PATH/R.java" \
    src/$PKG_PATH/*.java

if [ $? -ne 0 ]; then
    echo "Error de compilación en javac, build detenido (no se generó dex ni APK)."
    exit 1
fi

d8 --lib "$ANDROID_JAR" --output classes_dex $(find classes -name "*.class") || { echo "Falló d8"; exit 1; }

cd classes_dex && zip -uj ../app-unsigned.apk classes.dex && cd ..

java -jar "$SIGNER_JAR" \
    --apks app-unsigned.apk \
    --out output \
    --zipAlignPath "$(which zipalign)" || { echo "Falló la firma"; exit 1; }

# Función: copia el APK firmado a Descargas para instalarlo fácil
APK_FIRMADO=$(find output -name "*.apk" | head -n 1)

if [ -d "$DOWNLOADS" ]; then
    cp "$APK_FIRMADO" "$DOWNLOADS/LemeHostAuto.apk"
    echo "APK copiado a $DOWNLOADS/LemeHostAuto.apk"
else
    echo "No se encontró $DOWNLOADS — corré 'termux-setup-storage' una vez (te va a pedir permiso de almacenamiento) y volvé a correr el build."
fi

echo "Build completo. APK en: $ROOT/output/"
