
chmod +x ./gradlew
chmod +x ./jar/genJar.sh
./gradlew clean
./gradlew assembleRelease --no-daemon

./jar/genJar.sh

