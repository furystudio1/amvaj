package com.agntic.waves.Dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.leanback.app.GuidedStepFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;

import com.agntic.waves.ListVOD.VODvideo;
import com.agntic.waves.Main;
import com.agntic.waves.Music.MusicList;
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
import java.util.List;

public class Dialog extends Activity implements DialogContract {

    // Get Save
    SharedPreferences one_play_preferences;
    static SharedPreferences.Editor one_play_editor;
    int what;
    String url;

    private static final int STORAGE_PERMISSION_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            what = extras.getInt("what");
            //The key argument here must match that used in the other activity
        }

        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        one_play_editor = one_play_preferences.edit();
        url = one_play_preferences.getString("update", "");

        if (what == 1){

            GuidedStepFragment.add(getFragmentManager(), AlertSelectionFragment.newInstance());
        }else if(what == 2){

            GuidedStepFragment.add(getFragmentManager(), AlertSelectionFragment2.newInstance());
        }else if (what == 3){

            GuidedStepFragment.add(getFragmentManager(), AlertSelectionFragment3.newInstance());
        }else if (what == 4){

            GuidedStepFragment.add(getFragmentManager(), AlertSelectionFragment4.newInstance());
        }else if (what == 5){

            GuidedStepFragment.add(getFragmentManager(), AlertSelectionFragment5.newInstance());
        }else if (what == 6){

            GuidedStepFragment.add(getFragmentManager(), AlertSelectionFragment6.newInstance());
        }

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


    @Override
    public void onSelection(int positive) {

        if (what == 1){

            if(positive == 0){
                one_play_editor.putInt("Language", 1);
                one_play_editor.apply();

                Intent uou = new Intent(Dialog.this, Main.class);
                startActivity(uou);
                Dialog.this.finish();

            }else if (positive == 1){
                one_play_editor.putInt("Language", 2);
                one_play_editor.apply();

                Intent uou = new Intent(Dialog.this, Main.class);
                startActivity(uou);
                Dialog.this.finish();

            }else if (positive == 2){
                one_play_editor.putInt("Language", 3);
                one_play_editor.apply();

                Intent uou = new Intent(Dialog.this, Main.class);
                startActivity(uou);
                Dialog.this.finish();

            }

        }else if (what == 2){

            if(positive == 0){

                Dialog.this.finish();

            }
        }else if (what == 3){

            if(positive == 0){

                //start update
                //enqueueDownload();
                Intent uou = new Intent(Dialog.this, SingleDownloadActivity.class);
                startActivity(uou);
                Dialog.this.finish();

            }
        }else if (what == 4){

            if(positive == 0){

                //start update
                Intent uou = new Intent(Dialog.this, SingleDownloadActivity.class);
                startActivity(uou);
                Dialog.this.finish();

            }else if (positive == 1){

                Intent uou = new Intent(Dialog.this, Main.class);
                startActivity(uou);
                Dialog.this.finish();

            }
        }else if (what == 5){

            if(positive == 0){

                //start update
                Intent uou = new Intent(Dialog.this, VODvideo.class);
                startActivity(uou);
                Dialog.this.finish();

            }else if (positive == 1){

                Intent uou = new Intent(Dialog.this, Main.class);
                uou.putExtra("from",2);
                startActivity(uou);
                Dialog.this.finish();

            }
        }else if (what == 6){

            if(positive == 0){

                //start update
                Intent uou = new Intent(Dialog.this, MusicList.class);
                startActivity(uou);
                Dialog.this.finish();

            }else if (positive == 1){

                Intent uou = new Intent(Dialog.this, Main.class);
                uou.putExtra("from",2);
                startActivity(uou);
                Dialog.this.finish();

            }
        }

    }

    @Override
    public void onBackPressed() {

        if (what == 1){
            Intent uou = new Intent(Dialog.this, Main.class);
            startActivity(uou);
            Dialog.this.finish();
        }else if (what == 2){

            Dialog.this.finish();

        }else if (what == 3){

            Intent uou = new Intent(Dialog.this, Main.class);
            startActivity(uou);
            Dialog.this.finish();
        }else if (what == 4){

            Intent uou = new Intent(Dialog.this, Main.class);
            startActivity(uou);
            Dialog.this.finish();
        }else if (what == 5){

            Intent uou = new Intent(Dialog.this, Main.class);
            uou.putExtra("from",2);
            startActivity(uou);
            Dialog.this.finish();
        }else if (what == 6){

            Intent uou = new Intent(Dialog.this, Main.class);
            uou.putExtra("from",2);
            startActivity(uou);
            Dialog.this.finish();
        }

        super.onBackPressed();
    }

    private static void addAction(List<GuidedAction> actions, long id, String title, String desc) {
        actions.add(new GuidedAction.Builder()
                .id(id)
                .title(title)
                .description(desc)
                .build());
    }




    public static class AlertSelectionFragment extends BaseGuidedStepFragment<DialogContract> {

        public static AlertSelectionFragment newInstance() {
            AlertSelectionFragment fragment = new AlertSelectionFragment();
            return fragment;
        }
//        @Override
//        public int onProvideTheme() {
//            return R.style.PeirrTheme_Tv_GuidedStep_Connect;
//        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "Language Selection";
            String breadcrumb = "زبان نرم افزار انتخاب کنید";
            String description = "Select a language  /  اختر لغة";
            return new GuidanceStylist.Guidance(title, description, breadcrumb, null);
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, 0,
                    "فارسی","");
            addAction(actions, 1,
                    "English","");
            addAction(actions, 2,
                    "عربي","");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            if (action.getId() == 0) {
                getContract().onSelection(0);
            } else if (action.getId() == 1){
                getContract().onSelection(1);
            }else if (action.getId() == 2){
                getContract().onSelection(2);
            }
        }
    }


    public static class AlertSelectionFragment2 extends BaseGuidedStepFragment<DialogContract> {

        public static AlertSelectionFragment2 newInstance() {
            AlertSelectionFragment2 fragment = new AlertSelectionFragment2();
            return fragment;
        }
//        @Override
//        public int onProvideTheme() {
//            return R.style.PeirrTheme_Tv_GuidedStep_Connect;
//        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "خطا در احراز هویت";
            String breadcrumb = "";
            String description = "با پشتیبانی در ارتباط باشید";
            return new GuidanceStylist.Guidance(title, description, breadcrumb, null);
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, 0,
                    "بستن","");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            if (action.getId() == 0) {
                getContract().onSelection(0);
            }
        }
    }


    public static class AlertSelectionFragment3 extends BaseGuidedStepFragment<DialogContract> {

        public static AlertSelectionFragment3 newInstance() {
            AlertSelectionFragment3 fragment = new AlertSelectionFragment3();
            return fragment;
        }
//        @Override
//        public int onProvideTheme() {
//            return R.style.PeirrTheme_Tv_GuidedStep_Connect;
//        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "به روزرسانی";
            String breadcrumb = "";
            String description = "";
            return new GuidanceStylist.Guidance(title, description, breadcrumb, null);
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, 0,
                    "Update","دریافت نسخه جدید");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            if (action.getId() == 0) {
                getContract().onSelection(0);
            }
        }
    }

    public static class AlertSelectionFragment4 extends BaseGuidedStepFragment<DialogContract> {

        public static AlertSelectionFragment4 newInstance() {
            AlertSelectionFragment4 fragment = new AlertSelectionFragment4();
            return fragment;
        }
//        @Override
//        public int onProvideTheme() {
//            return R.style.PeirrTheme_Tv_GuidedStep_Connect;
//        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "خطا در به روزرسانی";
            String breadcrumb = "";
            String description = "";
            return new GuidanceStylist.Guidance(title, description, breadcrumb, null);
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, 0,
                    "Try again","دریافت مجدد");
            addAction(actions, 1,
                    "Close","بستن");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            if (action.getId() == 0) {
                getContract().onSelection(0);
            }else if (action.getId() == 1){
                getContract().onSelection(1);
            }
        }
    }

    public static class AlertSelectionFragment5 extends BaseGuidedStepFragment<DialogContract> {

        public static AlertSelectionFragment5 newInstance() {
            AlertSelectionFragment5 fragment = new AlertSelectionFragment5();
            return fragment;
        }
//        @Override
//        public int onProvideTheme() {
//            return R.style.PeirrTheme_Tv_GuidedStep_Connect;
//        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "خطا در اتصال به سرور";
            String breadcrumb = "";
            String description = "";
            return new GuidanceStylist.Guidance(title, description, breadcrumb, null);
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, 0,
                    "Try again","تلاش مجدد");
            addAction(actions, 1,
                    "Close","بستن");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            if (action.getId() == 0) {
                getContract().onSelection(0);
            }else if (action.getId() == 1){
                getContract().onSelection(1);
            }
        }
    }

    public static class AlertSelectionFragment6 extends BaseGuidedStepFragment<DialogContract> {

        public static AlertSelectionFragment6 newInstance() {
            AlertSelectionFragment6 fragment = new AlertSelectionFragment6();
            return fragment;
        }
//        @Override
//        public int onProvideTheme() {
//            return R.style.PeirrTheme_Tv_GuidedStep_Connect;
//        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public GuidanceStylist.Guidance onCreateGuidance(Bundle savedInstanceState) {
            String title = "خطا در اتصال به سرور";
            String breadcrumb = "";
            String description = "";
            return new GuidanceStylist.Guidance(title, description, breadcrumb, null);
        }

        @Override
        public void onCreateActions(List<GuidedAction> actions, Bundle savedInstanceState) {
            addAction(actions, 0,
                    "Try again","تلاش مجدد");
            addAction(actions, 1,
                    "Close","بستن");
        }

        @Override
        public void onGuidedActionClicked(GuidedAction action) {
            if (action.getId() == 0) {
                getContract().onSelection(0);
            }else if (action.getId() == 1){
                getContract().onSelection(1);
            }
        }
    }




}
