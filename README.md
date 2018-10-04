### Gradle引入
```
implementation 'com.dyhdyh.ffmpegjni:ffmpeg-java:1.0.0-beta2'
implementation 'com.dyhdyh.ffmpegjni:ffmpeg-armeabi-v7a:1.0.0-beta2'
```

### 执行命令行
#### 普通调用
```
FFmpegJNI.exec("ffmpeg", "-i", "test.mp4", "test.mp3");
```
#### RxJava调用
```
 FFmpegJNI.getInstance().execObservable("ffmpeg", "-i", "test.mp4", "test.mp3");
```

### 日志回调
#### 设置预设的日志回调
```
FFmpegJNI.getInstance().setLoggerListener(new SimpleFFmpegLoggerListener());
```

#### 自定义日志回调
```
public class CustomLoggerListener extends SimpleFFmpegLoggerListener{
    @Override
    public void onPrintMessage(int level, String message) {
        //自定义要做的事情
    }
}
```