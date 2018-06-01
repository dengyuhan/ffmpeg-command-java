# ffmpeg-command-java

#### 本工程的环境 
[ffmpeg 3.2.9](https://github.com/FFmpeg/FFmpeg/archive/n3.2.9.zip)  
[android-ndk-r12b-mac](https://dl.google.com/android/repository/android-ndk-r12b-darwin-x86_64.zip)

## 编译本工程
工程内置了ffmpeg-3.2.9，首次编译需要在AS中执行一次build脚本  

```
./ffmpeg-build/ffmpeg_configure.sh
```

## 编译FFmpeg
#### 1.进入到解压后的ffmpeg目录
```
cd ./ffmpeg-3.2.9
```

#### 2.初始化

```
./configure  --disable-yasm
```

#### 3.执行编译脚本
复制`ffmpeg-build`文件夹里的`build_android.sh`到ffmpeg目录，执行脚本

```
./build_android.sh
```
执行完会在`ffmpeg-3.2.9/android/armv7-a-vfp`生成一个`libffmpeg.so`文件