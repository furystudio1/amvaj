package com.agntic.waves.Dialog;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.agntic.waves.BuildConfig;
import com.agntic.waves.Main;
import com.agntic.waves.R;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;

import org.jetbrains.annotations.NotNull;

import java.io.File;


public class SingleDownloadActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 100;

    private View mainView;
    private TextView progressTextView;
    private TextView titleTextView;
    private TextView etaTextView;
    private TextView btn_install;
    // Get Save
    SharedPreferences one_play_preferences;
    static SharedPreferences.Editor one_play_editor;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_download);


        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        url = one_play_preferences.getString("update", "");

        mainView = findViewById(R.id.activity_single_download);
        progressTextView = findViewById(R.id.progressTextView);
        titleTextView = findViewById(R.id.titleTextView);
        etaTextView = findViewById(R.id.etaTextView);
        btn_install = findViewById(R.id.btn_install);
        PRDownloader.initialize(getApplicationContext());

        titleTextView.setText("در حال دانلود...");

        etaTextView.setText("لطفا تا اتمام دانلود صبر کنید.");
        btn_install.setText(" نصب کنید ");
        btn_install.setVisibility(View.GONE);
        btn_install.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String filePath2 = Data.getSaveDir(SingleDownloadActivity.this) + "/" + Data.getNameFromUrl(url);

                File toInstall = new File(filePath2);
                Intent intent;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Uri apkUri = FileProvider.getUriForFile(SingleDownloadActivity.this, BuildConfig.APPLICATION_ID + ".provider", toInstall);
                    intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    intent.setData(apkUri);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                } else {
                    Uri apkUri = Uri.fromFile(toInstall);
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                startActivity(intent);
                finish();
            }
        });

        final String filePath = Data.getSaveDir(this)  ;
        int downloadId = PRDownloader.download(url, filePath, Data.getNameFromUrl(url))
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercent = progress.currentBytes * 100 / progress.totalBytes;
                        String i = String.valueOf(progressPercent);
                        progressTextView.setText( i + " % ");
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        final String filePath2 = Data.getSaveDir(SingleDownloadActivity.this) + "/" + Data.getNameFromUrl(url);

                        btn_install.setVisibility(View.VISIBLE);
                        btn_install.hasFocus();

                        File toInstall = new File(filePath2);
                        Intent intent;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Uri apkUri = FileProvider.getUriForFile(SingleDownloadActivity.this, BuildConfig.APPLICATION_ID + ".provider", toInstall);
                            intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                            intent.setData(apkUri);
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } else {
                            Uri apkUri = Uri.fromFile(toInstall);
                            intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        }
                        startActivity(intent);

                    }

                    @Override
                    public void onError(com.downloader.Error error) {
                        Intent uou = new Intent(SingleDownloadActivity.this, Dialog.class);
                        uou.putExtra("what",4);
                        startActivity(uou);
                        SingleDownloadActivity.this.finish();
                    }
                });

        if (Status.PAUSED == PRDownloader.getStatus(downloadId)) {
            PRDownloader.resume(downloadId);
            return;
        }
        //Status status = PRDownloader.getStatus(downloadId);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
