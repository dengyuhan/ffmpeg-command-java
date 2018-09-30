#!/bin/bash
#脚本执行的路径
x264_name=x264-snapshot-20170705-2245

run_path=$(cd `dirname $0`; pwd)
cd $run_path

#如果没有x264文件夹 就解压
if [ ! -d $x264_name ]; then
  unzip $x264_name.zip -d $run_path
fi

chmod -R +x $x264_name
cd $x264_name
./configure --disable-asm

make

make install

#复制编译脚本到x264文件夹
target_path=$run_path/$x264_name/build_x264.sh
cp build_x264.sh $target_path
echo "复制x264编译脚本--->"$target_path

#编译x264
chmod -R +x $x264_name
cd $x264_name
./build_x264.sh