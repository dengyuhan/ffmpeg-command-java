basepath=$(cd `dirname $0`; pwd)
echo $basepath
cd $basepath

source build_config.sh

#解压ffmpeg
if [ ! -d $ffmpeg_name ]; then
  echo 解压ffmpeg......
  unzip -q $ffmpeg_name.zip
fi
cp -r build_config.sh $ffmpeg_name
cp -r $ffmpeg_script_name $ffmpeg_name
echo 复制编译ffmpeg脚本......

#解压libx264
libx264_path=$ffmpeg_name/$libx264_name
if [ ! -d $libx264_path ]; then
  echo 解压libx264......
  unzip -q $libx264_name.zip -d $ffmpeg_name
fi
cp -r $libx264_script_name $libx264_path
echo 复制编译libx264脚本......

#解压fdk_aac
fdk_aac_path=$ffmpeg_name/$fdk_aac_name
if [ ! -d $fdk_aac_path ]; then
  echo 解压fdk_aac......
  tar -xf $fdk_aac_name.tar.gz -C $ffmpeg_name
fi
cp -r $fdk_aac_script_name $fdk_aac_path
echo 复制编译fdk_aac脚本......

chmod -R +x $ffmpeg_name

#
cd $ffmpeg_name

cd $fdk_aac_name
./$fdk_aac_script_name

cd ..

cd $libx264_name
./$libx264_script_name

cd ..
./$ffmpeg_script_name
