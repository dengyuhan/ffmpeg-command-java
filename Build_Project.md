# 编译本工程


添加NDK根目录到环境变量并命名`ANDROID_NDK`

```
export ANDROID_NDK=~/Library/dev/android-ndk-r12b
export PATH=$ANDROID_NDK:$PATH
```
工程内置了ffmpeg-3.2.9，首次编译需要在AS中执行一次build脚本  

```
./ffmpeg-build/ffmpeg_configure.sh
```