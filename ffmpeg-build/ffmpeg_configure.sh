#!/bin/bash
#如果没有ffmpeg文件夹 就解压
if [ ! -d ./ffmpeg-jni/ffmpeg-3.2.9 ]; then
  unzip ./ffmpeg-build/ffmpeg-3.2.9.zip -d ./ffmpeg-jni
fi

#复制编译脚本到ffmpeg文件夹
cp ./ffmpeg-build-script/build_android.sh ./ffmpeg-jni/ffmpeg-3.2.9/build_android.sh

cd ./ffmpeg-jni/ffmpeg-3.2.9
./configure --disable-yasm
./build_android.sh