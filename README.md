## 日志回调
### 设置预设的日志回调
```
new SimpleFFmpegLoggerListener()
```

### 自定义日志回调
```
public class CustomLoggerListener extends SimpleFFmpegLoggerListener{
    @Override
    public void onPrintMessage(int level, String message) {
        //自定义要做的事情
    }
}
```