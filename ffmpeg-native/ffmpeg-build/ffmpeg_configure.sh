#!/bin/bash

#脚本执行的路径
ffmpeg_name=ffmpeg-3.2.9
run_path=$(cd `dirname $0`; pwd)
cd $run_path
echo "进入编译文件夹--->"$run_path

#如果没有FFmpeg文件夹 就解压
if [ ! -d $ffmpeg_name ]; then
  unzip $ffmpeg_name.zip -d $run_path
fi

#复制编译脚本到ffmpeg文件夹
target_path=$run_path/$ffmpeg_name/build_android.sh
cp build_android.sh $target_path
echo "复制编译脚本--->"$target_path

#编译x264并复制到ffmpeg文件夹
#target_x264_path=$run_path/$ffmpeg_name/$x264_name
#source ./x264_configure.sh
#cp -r $run_path/$x264_name $target_x264_path

#编译ffmpeg
chmod -R +x $ffmpeg_name
cd $ffmpeg_name

./configure --disable-yasm --enable-libx264 --enable-gpl

#编译FFmpeg
./build_android.sh