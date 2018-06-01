#!/bin/bash
unzip ./ffmpeg-build/ffmpeg-3.2.9.zip
cp ./ffmpeg-build-script/build_android.sh ./ffmpeg-jni/ffmpeg-3.2.9/build_android.sh
cd ./ffmpeg-jni/ffmpeg-3.2.9
./configure --disable-yasm
./build_android.sh