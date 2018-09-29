# 编译FFmpeg
#### 建议使用本工程的环境 
[ffmpeg 3.2.9](https://github.com/FFmpeg/FFmpeg/archive/n3.2.9.zip)  
[android-ndk-r12b-mac](https://dl.google.com/android/repository/android-ndk-r12b-darwin-x86_64.zip)

##### 两种编译方法任选其一

### 脚本自动编译
```
./ffmpeg_configure.sh
```

### 手动编译
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
