package com.dyhdyh.ffmpegjni.example;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dyhdyh.ffmpegjni.FFmpegJNI;

import java.io.File;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        String[] cutCmd = new String[]{"ffmpeg",
                "-ss", "00:00:00", "-t", "00:00:04",
                "-i", testFile.getAbsolutePath(), "-vcodec", "copy", "-acodec", "copy", "-y", outputFile.getAbsolutePath()};
        FFmpegJNI.getInstance()
                .execObservable(cutCmd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        Toast.makeText(MainActivity.this, "成功", Toast.LENGTH_SHORT).show();
                        Log.d("------------>", "成功----->" + integer);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "失败", Toast.LENGTH_SHORT).show();
                        Log.d("------------>", "失败----->" + e);
                    }

                    @Override
                    public void onComplete() {

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
