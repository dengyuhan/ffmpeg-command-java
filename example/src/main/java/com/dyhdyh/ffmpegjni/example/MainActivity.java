package com.dyhdyh.ffmpegjni.example;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dyhdyh.ffmpegjni.FFmpegJNI;
import com.dyhdyh.subscribers.loadingbar.rxjava2.SimpleLoadingDialogObserver;

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

        /*FFmpegJNI.getInstance().setLoggerListener(new OnFFmpegLoggerListener() {
            @Override
            public void onPrint(int level, byte[] messageByteArray) {
                //Log.d("----->", new String(messageByteArray));
            }
        });*/
    }


    public void clickStart(View view) {
        File outputFile = new File(getExternalCacheDir(), "output.mp4");
        //FFmpegJNI.exec("ffmpeg", "-i", testFile.getAbsolutePath(), outputFile.getAbsolutePath());
        String[] cutCmd = new String[]{"ffmpeg",
                "-ss", "00:00:00", "-t", "00:00:04",
                "-i", testFile.getAbsolutePath(), "-vcodec", "copy", "-acodec", "copy", "-y", outputFile.getAbsolutePath()};
        FFmpegJNI.getInstance()
                .execObservable(cutCmd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SimpleLoadingDialogObserver<Integer>(this, "正在执行", "执行失败") {
                    @Override
                    public void onNext(Integer integer) {
                        super.onNext(integer);
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
