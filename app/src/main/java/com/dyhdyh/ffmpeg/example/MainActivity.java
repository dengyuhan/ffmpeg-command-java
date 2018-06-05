package com.dyhdyh.ffmpeg.example;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.dyhdyh.ffmpeg.FFmpegJNI;
import com.dyhdyh.ffmpeg.listener.OnFFmpegResultListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    File testFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testFile = new File(getExternalCacheDir(), "test.mp4");
        FileUtils.copyAssetFile(this, "test.mp4", testFile);
    }

    public void clickStart(View view) {
        File outputFile = new File(getExternalCacheDir(), "output.mp4");
        //FFmpegJNI.exec("ffmpeg", "-i", testFile.getAbsolutePath(), outputFile.getAbsolutePath());
        FFmpegJNI.exec(new String[]{"ffmpeg",
                "-ss", "00:00:00", "-t", "00:00:04",
                "-i", testFile.getAbsolutePath(), "-vcodec", "copy", "-acodec", "copy", "-y", outputFile.getAbsolutePath()
        }, new OnFFmpegResultListener() {
            @Override
            public void onSuccess(int code) {
                Log.d("成功------>", code + "");
            }

            @Override
            public void onError(int code) {
                Log.d("失败------>", code + "");

            }
        });
        //FFmpegJNI.exec("ffmpeg", "-formats");

    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
    }
}
