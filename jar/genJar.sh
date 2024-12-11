 DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )"
 echo "$DIR"
rm -rf "$DIR"/custom_spider.jar
rm -rf "$DIR"/Smali_classes
mkdir -p "$DIR"/Smali_classes
java -jar "$DIR"/3rd/baksmali-2.5.2.jar d ./app/build/intermediates/dex/release/minifyReleaseWithR8/classes.dex -o "$DIR"/Smali_classes

rm -rf "$DIR"/spider.jar/smali/com/github/catvod/spider
rm -rf "$DIR"/spider.jar/smali/com/github/catvod/parser
rm -rf "$DIR"/spider.jar/smali/com/github/catvod/js


if [ ! -f "$DIR"/spider.jar/smali/com/github/catvod/ ]; then
    mkdir -p "$DIR"/spider.jar/smali/com/github/catvod/
fi

cp -rf "$DIR"/Smali_classes/com/github/catvod/spider "$DIR"/spider.jar/smali/com/github/catvod/
cp -rf "$DIR"/Smali_classes/com/github/catvod/parser "$DIR"/spider.jar/smali/com/github/catvod/
cp -rf "$DIR"/Smali_classes/com/github/catvod/js "$DIR"/spider.jar/smali/com/github/catvod/

java -jar "$DIR"/3rd/apktool_2.4.1.jar b "$DIR"/spider.jar -c

mv "$DIR"/spider.jar/dist/dex.jar "$DIR"/custom_spider.jar

md5 "$DIR"/custom_spider.jar | awk '{print $4}' > "$DIR"/custom_spider.jar.md5

rm -rf  "$DIR"/spider.jar/build
rm -rf  "$DIR"/spider.jar/smali
rm -rf  "$DIR"/spider.jar/dist
rm -rf  "$DIR"/Smali_classes
