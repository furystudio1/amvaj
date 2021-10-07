package com.agntic.waves.Dialog;


import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;


public final class Data {

    public static final String[] sampleUrls = new String[]{
            "http://speedtest.ftp.otenet.gr/files/test100Mb.db",
            "https://download.blender.org/peach/bigbuckbunny_movies/big_buck_bunny_720p_stereo.avi",
            "http://media.mongodb.org/zips.json",
            "http://www.exampletonyotest/some/unknown/123/Errorlink.txt",
            "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Android_logo_2019.svg/687px-Android_logo_2019.svg.png",
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"};

    private Data() {

    }

    @NonNull
    private static String getFilePath(@NonNull final String url, Context context) {
        final Uri uri = Uri.parse(url);
        final String fileName = uri.getLastPathSegment();
        final String dir = getSaveDir(context);
        return (dir + "/DownloadList/" + fileName);
    }

    @NonNull
    static String getNameFromUrl(final String url) {
        return Uri.parse(url).getLastPathSegment();
    }


    @NonNull
    public static String getSaveDir(Context context) {
        return context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/waves";
    }

}
