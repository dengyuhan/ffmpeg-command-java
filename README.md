### Gradle引入
```
implementation 'com.dyhdyh.ffmpegjni:ffmpeg-java:1.0.0-beta2'
implementation 'com.dyhdyh.ffmpegjni:ffmpeg-armeabi-v7a:1.0.0-beta2'
```

#### Debug开关
```
FFmpegJNI.getInstance().setDebug(true);
```

#### 普通调用
```
FFmpegJNI.getInstance().exec("ffmpeg", "-i", "test.mp4", "test.mp3");
```
#### RxJava调用
```
 FFmpegJNI.getInstance().execObservable("ffmpeg", "-i", "test.mp4", "test.mp3");
```
