# ffmpeg-command-java

#### 本工程的环境 
[ffmpeg 3.3.7](https://github.com/FFmpeg/FFmpeg/archive/n3.3.7.zip)  
[android-ndk-r12b](https://dl.google.com/android/repository/android-ndk-r12b-darwin-x86_64.zip)

#### 1.进入到解压后的ffmpeg目录
```
cd ./ffmpeg-3.4.2
```

#### 2.初始化
* 3.4.2以下  

```
./configure  --disable-yasm
```

* 3.4.2和以上  

```
./configure --disable-x86asm
```

#### 3.执行编译脚本
复制`build_android.sh`到ffmpeg目录，执行脚本

```
./build_android.sh
```
执行完会在`ffmpeg-3.3.7/android/armv7-a-vfp`生成一个`libffmpeg.so`文件