package com.dyhdyh.ffmpegjni.example;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dyhdyh.ffmpegjni.FFmpegJNI;
import com.dyhdyh.ffmpegjni.FFmpegStringBuilder;
import com.dyhdyh.ffmpegjni.listener.OnFFmpegProgressListener;
import com.dyhdyh.ffmpegjni.listener.impl.SimpleFFmpegLoggerListener;
import com.dyhdyh.subscriber.handler.LoadingHandler;
import com.dyhdyh.subscribers.loadingbar.handler.SimpleLoadingDialogHandler;
import com.dyhdyh.subscribers.loadingbar.rxjava2.SimpleLoadingDialogObserver;
import com.dyhdyh.widget.loading.dialog.LoadingDialog;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TextView tvLog;
    File testFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvLog = findViewById(R.id.tv_log);

        checkPermission();

        FFmpegJNI.getInstance().setDebug(true);

        testFile = new File(getExternalCacheDir(), "test.mp4");
        FileUtils.copyAssetFile(this, "test.mp4", testFile);
    }


    public void clickStart(View view) {
        final File outputFile = new File("s", "output.mp4");
        FFmpegStringBuilder builder = new FFmpegStringBuilder()
                .append("-ss")
                .append("00:00:00")
                .append("-t")
                .append("00:00:04")
                .append("-i")
                .append(testFile)
                .append("-vcodec")
                .append("h264")
                .append("-acodec")
                .append("copy")
                .append("-y")
                .append(outputFile);
        final float duration = 4000f;
        final LoadingDialog dialog = LoadingDialog.make(this);
        FFmpegJNI.getInstance()
                .setOnProgressListener(new OnFFmpegProgressListener() {
                    @Override
                    public void onProgress(float progressMillisecond) {
                        float progress = progressMillisecond / duration;
                        dialog.setMessage("正在处理 " + (int) (progress * 100) + "%");
                    }
                })
                .setLoggerListener(new SimpleFFmpegLoggerListener(BuildConfig.DEBUG))
                .execObservable(builder)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleLoadingDialogObserver<Integer>(this, "正在处理", "执行失败") {
                    @Override
                    public LoadingHandler<CharSequence> createLoadingHandler() {
                        return new SimpleLoadingDialogHandler(MainActivity.this) {
                            @Override
                            public void show(CharSequence params) {
                                if (!TextUtils.isEmpty(params)) {
                                    dialog.setMessage(params);
                                }
                                dialog.show();
                            }
                        };
                    }

                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
                        tvLog.setText(outputFile.getAbsolutePath());
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        Log.d("------------>", "成功----->" + integer);
                    }

                });


    }

    public void clickCodec(View view) {
        tvLog.setText(FFmpegJNI.avcodecInfo());
    }

    public void clickFormat(View view) {
        tvLog.setText(FFmpegJNI.avformatInfo());
    }

    public void clickFilter(View view) {
        tvLog.setText(FFmpegJNI.avfilterInfo());
    }

    public void clickConfig(View view) {
        tvLog.setText(FFmpegJNI.configurationInfo());
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }

}
