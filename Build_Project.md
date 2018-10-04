# 编译本工程

## 本工程使用的环境 
MacOS 10.13.4  
[android-ndk-r12b-mac](https://dl.google.com/android/repository/android-ndk-r12b-darwin-x86_64.zip)  
[ffmpeg 3.2.9](https://github.com/FFmpeg/FFmpeg/archive/n3.2.9.zip)  
[fdk-aac-0.1.6](https://downloads.sourceforge.net/opencore-amr/fdk-aac-0.1.6.tar.gz)  
[x264-snapshot-20170705-2245](http://download.videolan.org/pub/videolan/x264/snapshots/x264-snapshot-20170705-2245.tar.bz2)  

### 配置编译环境
添加NDK根目录到环境变量并命名`ANDROID_NDK`

```
export ANDROID_NDK=~/Library/dev/android-ndk-r12b
export PATH=$ANDROID_NDK:$PATH
```
* 如果不想用这个环境变量，那需要在脚本中修改成自己的NDK路径和  
[build\_fdk\_aac.sh](./ffmpeg-native/ffmpeg-build/build_fdk_aac.sh)  
[build_libx264.sh](./ffmpeg-native/ffmpeg-build/build_libx264.sh)  
[build_ffmpeg.sh](./ffmpeg-native/ffmpeg-build/build_ffmpeg.sh)   

### 编译配置
工程内置了需要的库  
[ffmpeg-3.2.9.zip](./ffmpeg-native/ffmpeg-build/ffmpeg-3.2.9.zip)  
[x264-snapshot-20170705-2245.zip](./ffmpeg-native/ffmpeg-build/x264-snapshot-20170705-2245.zip)  
[fdk-aac-0.1.6.zip](./ffmpeg-native/ffmpeg-build/fdk-aac-0.1.6.zip)  

如果要更换库或者脚本，需要在[build_config.sh](./ffmpeg-native/ffmpeg-build/build_config.sh)修改对应的名称；  
[CMakeLists.txt](./ffmpeg-native/CMakeLists.txt) 中的`include_directories`FFmpeg路径 

### 脚本自动编译
直接在Android Studio执行编译工程的脚本（约15-20分钟）

```
./ffmpeg-native/ffmpeg-build/build_project.sh
```
编译成功后，`.so`文件会生成在`/ffmpeg-native/ffmpeg-build/ffmpeg-3.2.9/android_more/arm/lib`



>手动编译FFmpeg  
[Build_FFmpeg.md](Build_FFmpeg.md)