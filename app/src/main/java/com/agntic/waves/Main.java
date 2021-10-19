package com.agntic.waves;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.leanback.app.GuidedStepFragment;
import androidx.leanback.widget.GuidanceStylist;
import androidx.leanback.widget.GuidedAction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.Dialog.BaseGuidedStepFragment;
import com.agntic.waves.Dialog.Dialog;
import com.agntic.waves.Dialog.DialogContract;
import com.agntic.waves.ListVOD.VODdetailVideo;
import com.agntic.waves.ListVOD.VODvideo;
import com.agntic.waves.ListVOD.adapters.MobileAdapter;
import com.agntic.waves.ListVOD.models.Brand;
import com.agntic.waves.ListVOD.models.Mobile;
import com.agntic.waves.Model.Video;
import com.agntic.waves.Model.VideoAdapter;
import com.agntic.waves.Music.MusicList;
import com.agntic.waves.db.DatabaseManager;
import com.agntic.waves.db.Person;
import com.agntic.waves.widget.Clock;
import com.agntic.waves.widget.OnClockTickListner;
import com.agntic.waves.widget.PermissionHandler;
import com.agntic.waves.widget.Utility;
import com.agntic.waves.widget.app_net;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class Main extends Activity {

    //http://109.125.130.155:2560/api/channel/grid

    ImageView stream, mainback, mainbackfilm, mainbackmusic, mainbackrozname, mainbackmusicw;
    TextView time1;
    TextView texttv;
    TextView textvideo;
    TextView textmusic;
    TextView roomtext;
    TextView weather;
    TextView textnews;
    TextView connection;
    TextView roomtext1;
    TextView time3;
    TextView wallet;
    TextView connection2;
    TextView textmusicw;
    static TextView namechannelmenu;
    TextView namechannelmenu2;

    RelativeLayout rl_suport, mainBG2;

    // Get Save
    SharedPreferences one_play_preferences;
    static SharedPreferences.Editor one_play_editor;
    String IPweb,Auth,AuthServer;
    String NameRoom;
    static String username;
    static String password;
    static String IPserver;
    static String lastchannel;
    static String SAMPLE_URL;
    String lastchannelname;
    int Custom, loginactive;
    private AudioManager audio;
    Clock c;

    boolean doubleBackToExitPressedOnce = false;
    boolean doubleBackToExitPressedOnce2 = false;
    boolean doubleBackToExitPressedOnce3 = true;

    private VLCVideoLayout mVideoLayout = null;

    private static LibVLC mLibVLC = null;
    private static MediaPlayer mMediaPlayer = null;

    int oneplay,language;
    //Time Splash (Millisecond)
    int _splashTime = 4000;

    TextView version1, firstcapaniname1, firstname1;

    ImageView firstimage1, firstback1;

    FrameLayout video_surface_frame_Menu;

    LinearLayout mainBG1,ramz, weatherclick, esm, connectiontab, walletclick,updateTab;

    WebView wb;
    WebView wb2;

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
    }

    String Url;
    String Url2;

    int where,from;

    private List<Video> videos = new ArrayList<>();
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    //private static final String SAMPLE_URL = "http://b:b@192.168.2.57:9981/stream/channel/5125412c26972527dc4742e7576773a9";
    //    private static final String SAMPLE_URL1 = "http://192.168.1.203:80/acer.mov";
    private static final int SURFACE_BEST_FIT = 0;
    private static int CURRENT_SIZE = SURFACE_BEST_FIT;
    private static int likenumber;
    private static String lastchannelset;

    static DrawerLayout drawerlayout;
    public static Activity fa;

    // Get Save
    String timemin;
    static String pID;
    ImageView hometv;
    static ImageView liketv;
    ImageView listtv;
    static TextView nametv;
    static TextView toplist;

    //set Loading
    ProgressWheel progress_wheel;

    static DatabaseManager dbm;

    static int likeOk = 0;

    boolean favoritelist;

    TextView clocktv, datetv;

    RelativeLayout tvinfoRe, tvlistRe;

    CardView view11,view,view3w,view4,view3;

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    boolean firsttime;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseCrashlytics.getInstance().log("message 1");

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //start activ
        setContentView(R.layout.root_listtv_livetv);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            from = extras.getInt("from");
            //The key argument here must match that used in the other activity
        }

        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (from == 1){
            _splashTime = 0;
        }else if (from == 2){
            _splashTime = 0;
        }

        fa = this;

        try {
            checkPermissions();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        try {
            dbm = new DatabaseManager(this);
            likenumber = dbm.personCount();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        try {
            // Get Save
            one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
            one_play_editor = one_play_preferences.edit();
            IPweb = one_play_preferences.getString("IPweb", "");
            NameRoom = one_play_preferences.getString("NameRoom", "Room 18");
            Custom = one_play_preferences.getInt("Custom", 1); ///1 , 2 , 3
            Auth = one_play_preferences.getString("auth", "1234"); ///1 , 2 , 3
            AuthServer = one_play_preferences.getString("authServer", "1234"); ///1 , 2 , 3
            username = one_play_preferences.getString("username", "");
            password = one_play_preferences.getString("password", "");
            IPserver = one_play_preferences.getString("IPserver", "109.125.130.155:2560");
            lastchannel = one_play_preferences.getString("lastchannel", "a534a6de6d60eff9a0254e8c933d7ba1");
            lastchannelname = one_play_preferences.getString("lastchannelname", "خوش آمدید");
            favoritelist = one_play_preferences.getBoolean("favoritelist", false);
            oneplay = one_play_preferences.getInt("one_play_app", 3);
            language = one_play_preferences.getInt("Language", 1);
            firsttime = one_play_preferences.getBoolean("60", true);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }


        if (firsttime){
            deleteCache(this);
            one_play_editor.putBoolean("60", false);
            one_play_editor.apply();
        }


        if (Auth.equals(AuthServer)){
            //access
        }else {
            //access denny
            Intent uou = new Intent(Main.this, Dialog.class);
            uou.putExtra("what",2);
            startActivity(uou);
            Main.this.finish();
        }


        try {
            stream = (ImageView) findViewById(R.id.stream);
            mainback = (ImageView) findViewById(R.id.mainback);
            mainbackrozname = (ImageView) findViewById(R.id.mainbackrozname);
            mainbackfilm = (ImageView) findViewById(R.id.mainbackfilm);
            mainbackmusic = (ImageView) findViewById(R.id.mainbackmusicw);
            mainbackmusicw = (ImageView) findViewById(R.id.mainbackmusic);
            time1 = (TextView) findViewById(R.id.time2);
            texttv = (TextView) findViewById(R.id.texttv);
            weather = (TextView) findViewById(R.id.weather);
            textvideo = (TextView) findViewById(R.id.textvideo);
            textmusic = (TextView) findViewById(R.id.textmusicw);
            textmusicw = (TextView) findViewById(R.id.textmusic);
            roomtext = (TextView) findViewById(R.id.roomtext);
            textnews = (TextView) findViewById(R.id.textnews);
            connection = (TextView) findViewById(R.id.connection);
            roomtext1 = (TextView) findViewById(R.id.roomtext1);
            wallet = (TextView) findViewById(R.id.wallet);
            time3 = (TextView) findViewById(R.id.time3);
            namechannelmenu = (TextView) findViewById(R.id.namechannelmenu);
            connection2 = (TextView) findViewById(R.id.connection2);
            namechannelmenu2 = (TextView) findViewById(R.id.namechannelmenu2);
            ramz = (LinearLayout) findViewById(R.id.ramz);
            weatherclick = (LinearLayout) findViewById(R.id.weatherclick);
            connectiontab = (LinearLayout) findViewById(R.id.connectiontab);
            walletclick = (LinearLayout) findViewById(R.id.walletclick);
            updateTab = (LinearLayout) findViewById(R.id.updateTab);
            esm = (LinearLayout) findViewById(R.id.esm);
            rl_suport = (RelativeLayout) findViewById(R.id.rl_suport);
            mainBG2 = (RelativeLayout) findViewById(R.id.mainBG2);
            tvinfoRe = (RelativeLayout)findViewById(R.id.tvinfoRe);
            tvlistRe = (RelativeLayout)findViewById(R.id.tvlistRe);
            version1 = (TextView) findViewById(R.id.version);
            firstcapaniname1 = (TextView) findViewById(R.id.firstcapaniname);
            firstname1 = (TextView) findViewById(R.id.firstname);
            firstimage1 = (ImageView) findViewById(R.id.firstimage);
            firstback1 = (ImageView) findViewById(R.id.firstback);
            video_surface_frame_Menu = (FrameLayout) findViewById(R.id.video_surface_frame_Menu);
            mainBG1 = (LinearLayout) findViewById(R.id.mainBG1);
            //Get Ready List
            recyclerView = (RecyclerView) findViewById(R.id.RecycleList);
            hometv = (ImageView) findViewById(R.id.hometv);
            liketv = (ImageView) findViewById(R.id.liketv);
            listtv = (ImageView) findViewById(R.id.listtv);
            nametv = (TextView) findViewById(R.id.nametv);
            clocktv = (TextView) findViewById(R.id.clocktv);
            toplist = (TextView) findViewById(R.id.toplist);
            datetv = (TextView) findViewById(R.id.datetv);
            mVideoLayout = findViewById(R.id.video_layout);
            drawerlayout = (DrawerLayout) findViewById(R.id.root_page_1);
            progress_wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
            view11 = (CardView) findViewById(R.id.view11);
            view  = (CardView) findViewById(R.id.view);
            view3w = (CardView) findViewById(R.id.view3w);
            view4 = (CardView) findViewById(R.id.view4);
            view3 = (CardView) findViewById(R.id.view3);

            updateTab.setVisibility(View.GONE);

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        if (language == 1){
            //farsi
            roomtext1.setText(R.string.room);
            time3.setText(R.string.clock);
            weather.setText(R.string.weather);
            wallet.setText(R.string.mmm);
            connection2.setText(R.string.net);
            texttv.setText(R.string.tv);
            textvideo.setText(R.string.video);
            textmusic.setText(R.string.music);
            textnews.setText(R.string.news);
            textmusicw.setText(R.string.www);

            try{
                if (lastchannelname.equals("خوش آمدید")){
                    nametv.setText(lastchannelname);
                    namechannelmenu2.setText(R.string.wlcname);
                }else {
                    nametv.setText(lastchannelname);
                    namechannelmenu2.setText(R.string.live);
                }
            }catch (Exception e){
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

            try {
                Utility util = new Utility();
                String ywd = util.getCurrentShamsidate();
                datetv.setText(setPersianNumbers(ywd));
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

        }else if (language == 2){
            //eng

            roomtext1.setText(R.string.roomEng);
            time3.setText(R.string.clockEng);
            weather.setText(R.string.weatherEng);
            weather.setText(R.string.weatherEng);
            wallet.setText(R.string.mmmEng);
            connection2.setText(R.string.netEng);
            texttv.setText(R.string.tvEng);
            textvideo.setText(R.string.videoEng);
            textmusic.setText(R.string.musicEng);
            textnews.setText(R.string.newsEng);
            textmusicw.setText(R.string.wwwEng);

            try{
                if (lastchannelname.equals("خوش آمدید")){
                    nametv.setText(R.string.wlcEng);
                    namechannelmenu2.setText(R.string.wlcnameEng);
                }else {
                    nametv.setText(lastchannelname);
                    namechannelmenu2.setText(R.string.liveEng);
                }
            }catch (Exception e){
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

            try {
                datetv.setText(DateFormat.format("dd-MM-yyyy", System.currentTimeMillis()).toString());
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

        } else if (language == 3) {
            //arabic

            roomtext1.setText(R.string.roomAR);
            time3.setText(R.string.clockAR);
            weather.setText(R.string.weatherAR);
            weather.setText(R.string.weatherAR);
            wallet.setText(R.string.mmmAR);
            connection2.setText(R.string.netAR);
            texttv.setText(R.string.tvAR);
            textvideo.setText(R.string.videoAR);
            textmusic.setText(R.string.musicAR);
            textnews.setText(R.string.newsAR);
            textmusicw.setText(R.string.wwwAR);
            try{
                if (lastchannelname.equals("خوش آمدید")){
                    nametv.setText(R.string.wlcAR);
                    namechannelmenu2.setText(R.string.wlcnameAR);
                }else {
                    nametv.setText(lastchannelname);
                    namechannelmenu2.setText(R.string.liveAR);
                }
            }catch (Exception e){
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

            try {
                datetv.setText(DateFormat.format("dd-MM-yyyy", System.currentTimeMillis()).toString());
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

        }

        try {
            roomtext.setText(NameRoom);
            //nametv.setText(lastchannelname);

            //Get file font
            Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

            time1.setTypeface(typeface);
            texttv.setTypeface(typeface);
            textvideo.setTypeface(typeface);
            textmusic.setTypeface(typeface);
            weather.setTypeface(typeface);
            roomtext.setTypeface(typeface);
            textnews.setTypeface(typeface);
            roomtext1.setTypeface(typeface);
            connection.setTypeface(typeface);
            wallet.setTypeface(typeface);
            connection2.setTypeface(typeface);
            namechannelmenu2.setTypeface(typeface);
            time3.setTypeface(typeface);
            version1.setTypeface(typeface);
            firstcapaniname1.setTypeface(typeface);
            firstname1.setTypeface(typeface);
            nametv.setTypeface(typeface);
            clocktv.setTypeface(typeface);
            toplist.setTypeface(typeface);
            datetv.setTypeface(typeface);
            textmusicw.setTypeface(typeface);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }


        //Focusable
        ramz.setFocusable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ramz.getFocusable();
        }
        ramz.setFocusableInTouchMode(true);
        weatherclick.setFocusable(true);
        weatherclick.setFocusableInTouchMode(true);
        connectiontab.setFocusable(true);
        connectiontab.setFocusableInTouchMode(true);
        walletclick.setFocusable(true);
        walletclick.setFocusableInTouchMode(true);
        updateTab.setFocusable(true);
        updateTab.setFocusableInTouchMode(true);
        esm.setFocusable(true);
        stream.setFocusable(true);
        mainbackfilm.setFocusable(true);
        mainbackmusicw.setFocusable(true);
        mainbackrozname.setFocusable(true);
        mainbackmusic.setFocusable(true);
        esm.setFocusableInTouchMode(true);
        stream.setFocusableInTouchMode(true);
        mainbackfilm.setFocusableInTouchMode(true);
        mainbackmusicw.setFocusableInTouchMode(true);
        mainbackrozname.setFocusableInTouchMode(true);
        mainbackmusic.setFocusableInTouchMode(true);

        try {
            //load Image
            Glide.with(this).load(getImage("main_bg")).thumbnail(0.5f).into(mainback);

            Glide.with(this).load(getImage("main_bg")).thumbnail(0.5f).into(firstback1);
            Glide.with(this).load(getImage("icon"))
                    .thumbnail(0.5f).into(firstimage1);
            Glide.with(this).load(getImage("home"))
                    .thumbnail(0.5f).into(hometv);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        try {
            namechannelmenu.setText(lastchannelname);
            namechannelmenu.setPaintFlags(namechannelmenu.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }


        try {
            /**videoAdapter.setOnLoadMoreLisener(new OnLoadMoreLisener() {
            @Override public void OnLoadMore() {
            video();
            }
            });*/
            if (favoritelist) {
                for (int i = 1; i <= likenumber; i++) {
                    Person vPer = dbm.getPerson(String.valueOf(i));
                    videos.add(new Video(vPer.pName, vPer.pUuid, String.valueOf(i)));
                }
                videoAdapter.notifyDataSetChanged();
                videoAdapter.setLoading(false);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress_wheel.setVisibility(View.GONE);
                    }
                }, 4000);

                if (language == 1){

                    toplist.setText(R.string.tvlistF);
                }else if (language == 2){

                    toplist.setText(R.string.tvlistFEng);
                }else if (language == 3){

                    toplist.setText(R.string.tvlistFAR);
                }

            } else {
                video(); ///IDK
                if (language == 1){

                    toplist.setText(R.string.tvlist);
                }else if (language == 2){

                    toplist.setText(R.string.tvlistEng);
                }else if (language == 3){

                    toplist.setText(R.string.tvlistAR);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress_wheel.setVisibility(View.GONE);
                    }
                }, 4000);
            }



            //Show Loading
            recyclerView.setNestedScrollingEnabled(false);
            progress_wheel.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(Main.this));
            videoAdapter = new VideoAdapter(videos, Main.this, recyclerView);
            recyclerView.setAdapter(videoAdapter);

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }




        //On Click
        /**stream.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        // button 1 was clicked!
        try {
        c.StopTick();
        mediaPlayer.stop();
        Intent uou3 = new Intent(Main.this, Stream.class);
        startActivity(uou3);
        Main.this.finish();
        } catch (Exception e) {
        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
        e.printStackTrace();
        }
        }
        });
         mainbackfilm.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        // button 1 was clicked!
        try {
        c.StopTick();
        mediaPlayer.stop();
        Intent uou3 = new Intent(Main.this, WebPage.class);
        uou3.putExtra("url", "http://" + username + ":" + password + "@" + IPweb + "/webservice/");
        startActivity(uou3);
        Main.this.finish();
        } catch (Exception e) {
        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
        e.printStackTrace();
        }
        }
        });
         mainbackmusic.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        // button 1 was clicked!
        try {
        c.StopTick();
        mediaPlayer.stop();
        Intent uou3 = new Intent(Main.this, WebPage.class);
        uou3.putExtra("url", "http://" + username + ":" + password + "@" + IPweb + "/webservice/");
        startActivity(uou3);
        Main.this.finish();
        } catch (Exception e) {
        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
        e.printStackTrace();
        }
        }
        });**/


        esm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // button 1 was clicked!

            }
        });


        esm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });


        connectiontab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // button 1 was clicked!

            }
        });

        connectiontab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });



        walletclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // button 1 was clicked!

                Intent uou = new Intent(Main.this, Dialog.class);
                uou.putExtra("what",1);
                startActivity(uou);
                Main.this.finish();
            }
        });


        walletclick.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        mainbackmusicw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // button 1 was clicked!
            }
        });

        updateTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // button 1 was clicked!

                Intent uou = new Intent(Main.this, Dialog.class);
                uou.putExtra("what",3);
                startActivity(uou);
                Main.this.finish();

            }
        });


        updateTab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        /**ring.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        // button 1 was clicked!
        try {
        /**Intent uou3 = new Intent(Main.this, notification.class);
        startActivity(uou3);
        Main.this.finish();
        } catch (Exception e) {
        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
        e.printStackTrace();
        }
        }
        });*/

        hometv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back
                try {
                    if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                            drawerlayout.closeDrawer(Gravity.LEFT);
                            drawerlayout.closeDrawer(Gravity.RIGHT);
                        } else {
                            drawerlayout.closeDrawer(Gravity.LEFT);
                        }
                    } else {
                        if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                            drawerlayout.closeDrawer(Gravity.RIGHT);
                        } else {
                            drawerlayout.openDrawer(Gravity.LEFT);
                            drawerlayout.openDrawer(Gravity.RIGHT);
                        }
                    }
                    Visible(1);

                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });

        hometv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();

                }else {

                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });


        liketv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back
                try {

                    if (likeOk == 0) {
                        int image = getIntent().getIntExtra("image", R.drawable.heart);
                        liketv.setImageResource(image);

                        likenumber = dbm.personCount();
                        Log.e("  error ", String.valueOf(likenumber));

                        lastchannel = one_play_preferences.getString("lastchannel", "a534a6de6d60eff9a0254e8c933d7ba1");
                        lastchannelname = one_play_preferences.getString("lastchannelname", "RTV");

                        Person iperson = new Person();
                        String id = String.valueOf(likenumber + 1);
                        iperson.pID = id;
                        iperson.pName = lastchannelname;
                        iperson.pUuid = lastchannel;
                        dbm.insertPerson(iperson);
                        likeOk = likenumber + 1;

                    } else {
                        //delete tv form favorite list
                        Person dperson = new Person();
                        dperson.pID = pID;
                        boolean del = dbm.deletePerson(dperson);

                        if (del) {
                            int image = getIntent().getIntExtra("image", R.drawable.heart_outline);
                            liketv.setImageResource(image);
                        } else {


                            if (language == 1){

                                Toast.makeText(Main.this, R.string.delete, Toast.LENGTH_LONG).show();
                            }else if (language == 2){

                                Toast.makeText(Main.this, R.string.deleteEng, Toast.LENGTH_LONG).show();
                            }else if (language == 3){

                                Toast.makeText(Main.this, R.string.deleteAR, Toast.LENGTH_LONG).show();
                            }

                        }

                        likenumber = dbm.personCount();
                    }


                    int sCount = dbm.personCount();


                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });

        liketv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();

                }else {

                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });



        listtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back
                try {

                    if (favoritelist) {
                        if (language == 1){

                            toplist.setText(R.string.tvlist);
                        }else if (language == 2){

                            toplist.setText(R.string.tvlistEng);
                        }else if (language == 3){

                            toplist.setText(R.string.tvlistAR);
                        }
                        videos.clear();
                        //progress_wheel.setVisibility(View.VISIBLE);
                        video(); ///IDK
                        favoritelist = false;

                        one_play_editor.putBoolean("favoritelist", favoritelist);
                        one_play_editor.apply();

                    } else {
                        if (language == 1){

                            toplist.setText(R.string.tvlistF);
                        }else if (language == 2){

                            toplist.setText(R.string.tvlistFEng);
                        }else if (language == 3){

                            toplist.setText(R.string.tvlistFAR);
                        }
                        if (likenumber == 0) {

                            if (language == 1){

                                Toast.makeText(Main.this, R.string.listEmpty, Toast.LENGTH_LONG).show();
                            }else if (language == 2){

                                Toast.makeText(Main.this, R.string.listEmptyEng, Toast.LENGTH_LONG).show();
                            }else if (language == 3){

                                Toast.makeText(Main.this, R.string.listEmptyAR, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            videos.clear();
                            progress_wheel.setVisibility(View.VISIBLE);
                            for (int i = 1; i <= likenumber; i++) {
                                Person vPer = dbm.getPerson(String.valueOf(i));
                                videos.add(new Video(vPer.pName, vPer.pUuid, String.valueOf(i)));
                            }
                            videoAdapter.notifyDataSetChanged();
                            videoAdapter.setLoading(false);
                            progress_wheel.setVisibility(View.GONE);

                            favoritelist = true;

                            one_play_editor.putBoolean("favoritelist", favoritelist);
                            one_play_editor.apply();

                        }
                    }

                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });



        listtv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();

                }else {

                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });


        video_surface_frame_Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (where == 5) {
                    try {
                        //sliding view
                        if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                            if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                                drawerlayout.closeDrawer(Gravity.LEFT);
                                drawerlayout.closeDrawer(Gravity.RIGHT);
                            } else {
                                drawerlayout.closeDrawer(Gravity.LEFT);
                            }
                        } else {
                            if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                                drawerlayout.closeDrawer(Gravity.RIGHT);
                            } else {
                                drawerlayout.openDrawer(Gravity.LEFT);
                                drawerlayout.openDrawer(Gravity.RIGHT);
                            }
                        }
                    } catch (Exception e) {
                        FirebaseCrashlytics.getInstance().recordException(e);
                        e.printStackTrace();
                    }
                }

            }
        });

        /**try {
         if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
         if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
         drawerlayout.closeDrawer(Gravity.LEFT);
         drawerlayout.closeDrawer(Gravity.RIGHT);
         } else {
         drawerlayout.closeDrawer(Gravity.LEFT);
         }
         } else {
         if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
         drawerlayout.closeDrawer(Gravity.RIGHT);
         } else {
         drawerlayout.openDrawer(Gravity.LEFT);
         drawerlayout.openDrawer(Gravity.RIGHT);
         }
         }
         } catch (Exception e) {
         Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
         e.printStackTrace();
         }**/


        mainbackfilm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //c.StopTick();
                    //mMediaPlayer.stop();
                    //mMediaPlayer.detachViews();

                    //Visible(3);

                    Intent uou = new Intent(Main.this, VODvideo.class);
                    startActivity(uou);
                    Main.this.finish();


                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });

        mainbackmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent uou = new Intent(Main.this, MusicList.class);
                    startActivity(uou);
                    Main.this.finish();

                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });

        mainbackrozname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {


                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });

        weatherclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    c.StopTick();
                    mMediaPlayer.stop();
                    mMediaPlayer.detachViews();

                    Visible(4);

                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });

        weatherclick.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        stream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Visible(5);

                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });

        loginactive = 0;
        ramz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginactive == 0) {
                    loginactive = 1;
                } else if (loginactive == 1) {
                    loginactive = 2;
                } else if (loginactive == 2) {
                    loginactive = 3;
                } else if (loginactive == 3) {
                    loginactive = 4;
                } else if (loginactive == 4) {
                    loginactive = 0;
                    try {
                        Toast.makeText(Main.this, "لطفا احراز هویت کنید", Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        FirebaseCrashlytics.getInstance().recordException(e);
                        e.printStackTrace();
                    }

                    try {
                        one_play_editor.putInt("one_play_app", 3);
                        one_play_editor.apply();
                    } catch (Exception e) {
                        FirebaseCrashlytics.getInstance().recordException(e);
                        e.printStackTrace();
                    }

                    try {
                        c.StopTick();
                        Intent uou3 = new Intent(Main.this, Login.class);
                        startActivity(uou3);
                        Main.this.finish();
                    } catch (Exception e) {
                        FirebaseCrashlytics.getInstance().recordException(e);
                        e.printStackTrace();
                    }

                }
            }
        });

        ramz.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        try {
            //Date currentTime = Calendar.getInstance().getTime();

            //time1.setText(DateFormat.format("h:mm aa", currentTime).toString());
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        c = new Clock(this, 0);
        c.AddClockTickListner(new OnClockTickListner() {

            @Override
            public void OnSecondTick(Time currentTime) {
                //Log.d("Tick Test per Second", DateFormat.format("h:mm:ss aa ", currentTime.toMillis(true)).toString());
                try {
                    if (language == 1){

                        time1.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                        clocktv.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                    }else if (language == 2){

                        time1.setText(DateFormat.format("h : mm", currentTime.toMillis(true)).toString());
                        clocktv.setText(DateFormat.format("h : mm", currentTime.toMillis(true)).toString());
                    }else if (language == 3){

                        time1.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                        clocktv.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                    }
                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }

                try {
                    //check net
                    if (app_net.getInstance(Main.this).isOnline()) {

                        if (language == 1){

                            connection.setText(R.string.connectionOn);
                        }else if (language == 2){

                            connection.setText(R.string.connectionOnEng);
                        }else if (language == 3){

                            connection.setText(R.string.connectionOnAR);
                        }

                    } else {

                        if (language == 1){

                            connection.setText(R.string.connectionOff);
                        }else if (language == 2){

                            connection.setText(R.string.connectionOffEng);
                        }else if (language == 3){

                            connection.setText(R.string.connectionOffAR);
                        }

                    }
                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }

            }

            @Override
            public void OnMinuteTick(Time currentTime) {
                //Log.d("Tick Test per Minute",DateFormat.format("h:mm aa", currentTime.toMillis(true)).toString());
                try {
                    if (language == 1){

                        time1.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                        clocktv.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                    }else if (language == 2){

                        time1.setText(DateFormat.format("h : mm", currentTime.toMillis(true)).toString());
                        clocktv.setText(DateFormat.format("h : mm", currentTime.toMillis(true)).toString());
                    }else if (language == 3){

                        time1.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                        clocktv.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
                    }
                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });


        //Stream background

        mVideoLayout = findViewById(R.id.video_layout_Menu);

        try {
            SAMPLE_URL = "http://" + username + ":" + password + "@" + IPserver + "/stream/channel/" + lastchannel;
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        try {
            final ArrayList<String> args = new ArrayList<>();
            ///args.add("--vout=android-display");
            //args.add("--no-drop-late-frames");
            ///args.add("--no-skip-frames");
            ///args.add("--avcodec-fast");
            ////args.add("--network-caching=1000");
            ///args.add("--audio-time-stretch"); // time stretching
            //args.add("--h264-fps=30"); // time stretching
            ///args.add("--rtsp-tcp");
            args.add("--sout");
            args.add("--sout-ts-es-id-pid");
            args.add("--ts-es-id-pid");
            args.add("--sout-all");
            ///args.add("-vvv");

            mLibVLC = new LibVLC(this, args);
            mMediaPlayer = new MediaPlayer(mLibVLC);
//        mMediaPlayer2 = new MediaPlayer(mLibVLC);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
        try {
            mMediaPlayer.attachViews(mVideoLayout, null, false, false);
//        mVideoSurfaceFrame1 = (FrameLayout) findViewById(R.id.video_surface_frame1);
//        mVideoSurface1 = (SurfaceView) findViewById(R.id.video_surface1);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
        try {
            Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
            ///media.setHWDecoderEnabled(true, false);       //new
            //media.addOption(":network-caching=3000");
            ///media.addOption(":clock-jitter=0");
            ///media.addOption(":clock-synchro=0");       //new
            //media.addOption(":sout-record-dst-prefix=yylpre.mp4");
            media.addOption(":network-caching=1000");
            //media.addOption(":rtsp-frame-buffer-size=100000");
            ///media.addOption(":rtsp-tcp");
            //media.addOption(":fullscreen");
            //media.addOption(":rtsp-timeout=300");
            //media.addOption(":codec=mediacodec_ndk,mediacodec_jni,none");
            mMediaPlayer.setMedia(media);
            //mMediaPlayer.setAspectRatio("16:9");
            //mMediaPlayer.setAspectRatio("4:3");
            //mMediaPlayer.setScale(1.8f);
            media.release();
            mMediaPlayer.play();
            //mMediaPlayer.setRate(.5f);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }


        Url = "http://" + IPweb + "/webservice/";
        //Url = "https://www.filimo.com/";


        if (language == 1){

            Url2 = "https://weather.com/fa-IR/weather/tenday/l/99af92d56c39592dd032045644262b7e574ff1d0c5fe7f8ca1603a0440491d79";
        }else if (language == 2){

            Url2 = "https://weather.com/en/weather/tenday/l/99af92d56c39592dd032045644262b7e574ff1d0c5fe7f8ca1603a0440491d79";
        }else if (language == 3){

            Url2 = "https://weather.com/ar/weather/tenday/l/99af92d56c39592dd032045644262b7e574ff1d0c5fe7f8ca1603a0440491d79";
        }


        try {
            //new FinestWebView.Builder(this).webViewJavaScriptEnabled(true).webViewUseWideViewPort(true).showUrl(false).show("https://www.google.com/");
            wb = (WebView) findViewById(R.id.webView1);
            wb.getSettings().setJavaScriptEnabled(true);
            wb.getSettings().setLoadWithOverviewMode(true);
            wb.getSettings().setUseWideViewPort(true);
            wb.getSettings().setBuiltInZoomControls(true);
            wb.getSettings().setDisplayZoomControls(false);
            wb.setScrollbarFadingEnabled(true);
            wb.setVerticalScrollBarEnabled(false);
            wb.getSettings().setPluginState(WebSettings.PluginState.ON);
            wb.setWebViewClient(new HelloWebViewClient());
            //wb.loadUrl("https://www.google.com");
            //wb.loadUrl("http://192.168.2.98/webservice/2020/12/17/hello-world/");
            wb.loadUrl(Url);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        try {
            //new FinestWebView.Builder(this).webViewJavaScriptEnabled(true).webViewUseWideViewPort(true).showUrl(false).show("https://www.google.com/");
            wb2 = (WebView) findViewById(R.id.webView2);
            wb2.getSettings().setJavaScriptEnabled(true);
            wb2.getSettings().setLoadWithOverviewMode(true);
            wb2.getSettings().setUseWideViewPort(true);
            wb2.getSettings().setBuiltInZoomControls(true);
            wb2.getSettings().setDisplayZoomControls(false);
            wb2.setScrollbarFadingEnabled(true);
            wb2.setVerticalScrollBarEnabled(false);
            wb2.getSettings().setPluginState(WebSettings.PluginState.ON);
            wb2.setWebViewClient(new HelloWebViewClient());
            //wb.loadUrl("https://www.google.com");
            //wb.loadUrl("http://192.168.2.98/webservice/2020/12/17/hello-world/");
            wb2.loadUrl(Url2);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }


        for (int i = 1; i <= likenumber; i++) {

            Person vPer = dbm.getPerson(String.valueOf(i));
            if (lastchannel.equals(vPer.pUuid)) {
                pID = vPer.pID;
                likeOk = 1;
                int image = getIntent().getIntExtra("image", R.drawable.heart);
                liketv.setImageResource(image);
            }
        }

        lastchannelset = lastchannel;

        auth_update();




        view11.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        view  .setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        view3w.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        view4 .setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });

        view3 .setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });


    }

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }

    private void checkPermissions() {

        String[] per = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_PHONE_STATE, android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.REQUEST_INSTALL_PACKAGES};

        new PermissionHandler().checkPermission(this, per, new PermissionHandler.OnPermissionResponse() {
            @Override
            public void onPermissionGranted() {
                // permission granted
                // your code
            }

            @Override
            public void onPermissionDenied() {
                // User canceled permission
                try {
                    Toast.makeText(Main.this, "در صورت نپذیرفتن درخواست ها برنامه با مشکل مواجه می شود!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mMediaPlayer.release();
            mLibVLC.release();
            c.StopTick();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mMediaPlayer.stop();
            mMediaPlayer.detachViews();
            c.StopTick();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (where == 1) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                try {
                    mMediaPlayer.stop();
                    mMediaPlayer.detachViews();
                    c.StopTick();
                    // Go TO Page Back (Page 1 or MainActivity)
                    Main.this.finish();
                } catch (Exception e) {
                    FirebaseCrashlytics.getInstance().recordException(e);
                    e.printStackTrace();
                }
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
            // Go TO Page Back (Page 1 or MainActivity)
        } else if (where == 3) {

            try {
                SAMPLE_URL = "http://" + username + ":" + password + "@" + IPserver + "/stream/channel/" + lastchannel;
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

            try {
                final ArrayList<String> args = new ArrayList<>();
                ///args.add("--vout=android-display");
                //args.add("--no-drop-late-frames");
                ///args.add("--no-skip-frames");
                ///args.add("--avcodec-fast");
                args.add("--network-caching=1000");
                ///args.add("--audio-time-stretch"); // time stretching
                //args.add("--h264-fps=30"); // time stretching
                ///args.add("--rtsp-tcp");
                ////args.add("-vvv");
                args.add("--sout");
                args.add("--sout-ts-es-id-pid");
                args.add("--ts-es-id-pid");
                args.add("--sout-all");

                mLibVLC = new LibVLC(this, args);
                mMediaPlayer = new MediaPlayer(mLibVLC);
//        mMediaPlayer2 = new MediaPlayer(mLibVLC);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }
            try {
                mMediaPlayer.attachViews(mVideoLayout, null, false, false);
//        mVideoSurfaceFrame1 = (FrameLayout) findViewById(R.id.video_surface_frame1);
//        mVideoSurface1 = (SurfaceView) findViewById(R.id.video_surface1);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }
            try {
                Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
                ///media.setHWDecoderEnabled(true, false);       //new
                //media.addOption(":network-caching=3000");
                ///media.addOption(":clock-jitter=0");
                ///media.addOption(":clock-synchro=0");       //new
                //media.addOption(":sout-record-dst-prefix=yylpre.mp4");
                media.addOption(":network-caching=1000");
                //media.addOption(":rtsp-frame-buffer-size=100000");
                ///media.addOption(":rtsp-tcp");
                //media.addOption(":fullscreen");
                //media.addOption(":rtsp-timeout=300");
                //media.addOption(":codec=mediacodec_ndk,mediacodec_jni,none");
                mMediaPlayer.setMedia(media);
                //mMediaPlayer.setAspectRatio("16:9");
                //mMediaPlayer.setAspectRatio("4:3");
                //mMediaPlayer.setScale(1.8f);
                media.release();
                mMediaPlayer.play();
                //mMediaPlayer.setRate(.5f);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

            Visible(1);


        } else if (where == 4) {

            try {
                SAMPLE_URL = "http://" + username + ":" + password + "@" + IPserver + "/stream/channel/" + lastchannel;
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

            try {
                final ArrayList<String> args = new ArrayList<>();
                ///args.add("--vout=android-display");
                //args.add("--no-drop-late-frames");
                ///args.add("--no-skip-frames");
                ///args.add("--avcodec-fast");
                args.add("--network-caching=1000");
                ///args.add("--audio-time-stretch"); // time stretching
                //args.add("--h264-fps=30"); // time stretching
                ///args.add("--rtsp-tcp");
                ////args.add("-vvv");
                args.add("--sout");
                args.add("--sout-ts-es-id-pid");
                args.add("--ts-es-id-pid");
                args.add("--sout-all");

                mLibVLC = new LibVLC(this, args);
                mMediaPlayer = new MediaPlayer(mLibVLC);
//        mMediaPlayer2 = new MediaPlayer(mLibVLC);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }
            try {
                mMediaPlayer.attachViews(mVideoLayout, null, false, false);
//        mVideoSurfaceFrame1 = (FrameLayout) findViewById(R.id.video_surface_frame1);
//        mVideoSurface1 = (SurfaceView) findViewById(R.id.video_surface1);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }
            try {
                Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
                ///media.setHWDecoderEnabled(true, false);       //new
                //media.addOption(":network-caching=3000");
                ///media.addOption(":clock-jitter=0");
                ///media.addOption(":clock-synchro=0");       //new
                //media.addOption(":sout-record-dst-prefix=yylpre.mp4");
                media.addOption(":network-caching=1000");
                //media.addOption(":rtsp-frame-buffer-size=100000");
                ///media.addOption(":rtsp-tcp");
                //media.addOption(":fullscreen");
                //media.addOption(":rtsp-timeout=300");
                //media.addOption(":codec=mediacodec_ndk,mediacodec_jni,none");
                mMediaPlayer.setMedia(media);
                //mMediaPlayer.setAspectRatio("16:9");
                //mMediaPlayer.setAspectRatio("4:3");
                //mMediaPlayer.setScale(1.8f);
                media.release();
                mMediaPlayer.play();
                //mMediaPlayer.setRate(.5f);
            } catch (Exception e) {
                FirebaseCrashlytics.getInstance().recordException(e);
                e.printStackTrace();
            }

            Visible(1);


        } else if (where == 5) {


            if (doubleBackToExitPressedOnce2) {
                if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                    if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerlayout.closeDrawer(Gravity.LEFT);
                        drawerlayout.closeDrawer(Gravity.RIGHT);
                    } else {
                        drawerlayout.closeDrawer(Gravity.LEFT);
                    }
                } else {
                    if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                        drawerlayout.closeDrawer(Gravity.RIGHT);
                    }
                }
                Visible(1);
            }else {
                if (doubleBackToExitPressedOnce3){
                    doubleBackToExitPressedOnce3 = false;
                    if (drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                            drawerlayout.closeDrawer(Gravity.LEFT);
                            drawerlayout.closeDrawer(Gravity.RIGHT);
                        } else {
                            drawerlayout.closeDrawer(Gravity.LEFT);
                        }
                    } else {
                        if (drawerlayout.isDrawerOpen(Gravity.RIGHT)) {
                            drawerlayout.closeDrawer(Gravity.RIGHT);
                        } else {
                            drawerlayout.openDrawer(Gravity.LEFT);
                            drawerlayout.openDrawer(Gravity.RIGHT);
                        }
                    }
                }

            }
            doubleBackToExitPressedOnce2 = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce2 = false;
                }
            }, 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce3 = true;
                }
            }, 3000);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent("PERMISSION_RECEIVER");
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("permissions", permissions);
        intent.putExtra("grantResults", grantResults);
        sendBroadcast(intent);
    }

    private String setPersianNumbers(String str) {
        return str
                .replace("0", "۰")
                .replace("1", "۱")
                .replace("2", "۲")
                .replace("3", "۳")
                .replace("4", "۴")
                .replace("5", "۵")
                .replace("6", "۶")
                .replace("7", "۷")
                .replace("8", "۸")
                .replace("9", "۹")
                .replace("PM", "بعد از ظهر")
                .replace("AM", "قبل از ظهر");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Visible(2);

        if (oneplay == 3) {
            try {

                //Set Record
                one_play_editor.putInt("one_play_app", 3);
                one_play_editor.apply();

                new Handler().postDelayed(new Thread() {

                    @Override
                    public void run() {
                        super.run();

                        //Go to Page Slider
                        Intent uou = new Intent(Main.this, Login.class);
                        startActivity(uou);
                        Main.this.finish();

                    }
                }, _splashTime);

            } catch (Resources.NotFoundException e) {
            }
        } else if (oneplay == 4) {

            try {

                new Handler().postDelayed(new Thread() {

                    @Override
                    public void run() {
                        super.run();
                        //Go to Page Login
                        //Visible
                        Visible(1);
                    }
                }, _splashTime);

            } catch (Exception e) {
            }

        }

    }


    //get all data from rest
    public void video() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Main.this);
            String url = "http://" + IPserver + "/api/channel/grid";
            //String url = "http://109.125.130.155:2560//api/channel/grid";
            //String url = "http://46.100.60.180/vod/json.php";

        /*JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.w("  All Json loge ", String.valueOf(response));
                    //Get 10 Item After Loading
                    numberLoadItem += 10;
                    if (numberLoadItem > response.length()){
                        numberLoadItem = response.length();
                    }
                    for (int i = numberLoad; i < numberLoadItem; i++) {
                        numberLoad ++ ;
                        JSONObject jsonObject = response.getJSONObject(0);
                        String Title = null;
                        String Url = null;
                        String Numb = String.valueOf(numberLoad);
                        if (!jsonObject.isNull("name")) {
                            Title = jsonObject.getString("name");
                        }
                        if (!jsonObject.isNull("uuid")) {
                            Url = jsonObject.getString("uuid");
                        }
                        Log.w("  All Json loge "+ numberLoad, String.valueOf(Title));
                        videos.add(new Video(Title, Url, Numb));
                    }
                    videoAdapter.notifyDataSetChanged();
                    videoAdapter.setLoading(false);
                } catch (JSONException e) {
                    //Error Code
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // do something
            }
        });*/

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //VolleyLog.v("Response:%n %s", response.toString(4));

                        JSONArray students = response.getJSONArray("entries");

                        // looping through All Students
                        for (int i = 0; i < students.length(); i++) {
                            JSONObject c = students.getJSONObject(i);

                            int ii = i + 1;
                            String Title = null;
                            String Url = null;
                            String Numb = String.valueOf(ii);
                            if (Numb.length() >= 0) {
                                String NN;
                                if (Numb.length() == 1) {
                                    NN = "000" + Numb;
                                    Numb = NN;
                                } else if (Numb.length() == 2) {
                                    NN = "00" + Numb;
                                    Numb = NN;
                                } else if (Numb.length() == 3) {
                                    NN = "0" + Numb;
                                    Numb = NN;
                                }
                            }
                            if (!c.isNull("name")) {
                                Title = c.getString("name");
                            }
                            if (!c.isNull("uuid")) {
                                Url = c.getString("uuid");
                            }
                            videos.add(new Video(Title, Url, Numb));
                        }
                        videoAdapter.notifyDataSetChanged();
                        videoAdapter.setLoading(false);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress_wheel.setVisibility(View.GONE);
                            }
                        }, 4000);
                    } catch (JSONException e) {
                        Log.e("  error ", "fffff");
                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", "eerror");
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", "b", "b");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };


            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        videos.add(new Video("Title", "Url", "Numb"));

    }

    //get all data from rest
    public static void TV(String uuid, String name) {
        try {
            //mMediaPlayer.stop();
            //mMediaPlayer.getVLCVout().removeCallback(Stream.this);
            lastchannelset = uuid;
            SAMPLE_URL = "http://" + username + ":" + password + "@" + IPserver + "/stream/channel/" + uuid;
            Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
            // media.addOption(":network-caching=3000");
            //media.addOption(":clock-jitter=0");
            //media.addOption(":rtsp-timeout=300");
            //media.addOption(":codec=mediacodec_ndk,mediacodec_jni,none");
            ///media.setHWDecoderEnabled(true, false);       //new
            ///media.addOption(":clock-jitter=0");
            ///media.addOption(":clock-synchro=0");       //new
            media.addOption(":network-caching=1000");
            ///media.addOption(":rtsp-tcp");
            mMediaPlayer.setMedia(media);
            media.release();
            mMediaPlayer.play();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
        try {
            nametv.setText(name);
            namechannelmenu.setText(name);
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }


        try {
            one_play_editor.putString("lastchannel", uuid);
            one_play_editor.putString("lastchannelname", name);
            one_play_editor.apply();
        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }

        favorite();

        drawerlayout.closeDrawers();

    }

    //set icon favorite
    public static void favorite() {
        if (lastchannel.equals(lastchannelset)) {

        } else {
            likenumber = dbm.personCount();
            for (int i = 1; i <= likenumber; i++) {
                Person vPer = dbm.getPerson(String.valueOf(i));
                if (lastchannelset.equals(vPer.pUuid)) {
                    likeOk = 1;
                    pID = vPer.pID;
                    int image = fa.getIntent().getIntExtra("image", R.drawable.heart);
                    liketv.setImageResource(image);

                } else if (i == likenumber) {
                    likeOk = 0;
                    pID = vPer.pID;
                    int image = fa.getIntent().getIntExtra("image", R.drawable.heart_outline);
                    liketv.setImageResource(image);
                }
            }

            lastchannel = lastchannelset;
        }
    }


    public void Visible(int n) {
        if (n == 1) {
            //hide start, show main page
            where = 1;
            rl_suport.setVisibility(View.INVISIBLE);
            video_surface_frame_Menu.setVisibility(View.VISIBLE);
            mainBG1.setVisibility(View.VISIBLE);
            mainBG2.setVisibility(View.VISIBLE);
            wb.setVisibility(View.INVISIBLE);
            wb2.setVisibility(View.INVISIBLE);
            tvinfoRe.setVisibility(View.INVISIBLE);
            tvlistRe.setVisibility(View.INVISIBLE);

            drawerlayout.closeDrawers();
            //focus
            ramz.setFocusable(true);
            esm.setFocusable(true);
            weatherclick.setFocusable(true);
            walletclick.setFocusable(true);
            updateTab.setFocusable(true);
            connectiontab.setFocusable(true);
            stream.setFocusable(true);
            mainbackfilm.setFocusable(true);
            mainbackmusicw.setFocusable(true);
            mainbackrozname.setFocusable(true);
            mainbackmusic.setFocusable(true);

            liketv.setFocusable(false);
            listtv.setFocusable(false);
            hometv.setFocusable(false);
            video_surface_frame_Menu.setFocusable(false);
        } else if (n == 2) {
            //show start, hide main page
            where = 2;
            rl_suport.setVisibility(View.VISIBLE);
            video_surface_frame_Menu.setVisibility(View.INVISIBLE);
            mainBG1.setVisibility(View.INVISIBLE);
            mainBG2.setVisibility(View.INVISIBLE);
            wb.setVisibility(View.INVISIBLE);
            wb2.setVisibility(View.INVISIBLE);
            tvinfoRe.setVisibility(View.INVISIBLE);
            tvlistRe.setVisibility(View.INVISIBLE);
            drawerlayout.closeDrawers();
        } else if (n == 3) {
            //hide start, hide main page, show web1
            where = 3;
            rl_suport.setVisibility(View.INVISIBLE);
            video_surface_frame_Menu.setVisibility(View.INVISIBLE);
            mainBG1.setVisibility(View.INVISIBLE);
            mainBG2.setVisibility(View.INVISIBLE);
            wb.setVisibility(View.VISIBLE);
            wb2.setVisibility(View.INVISIBLE);
            tvinfoRe.setVisibility(View.INVISIBLE);
            tvlistRe.setVisibility(View.INVISIBLE);

            //focus
            ramz.setFocusable(false);
            esm.setFocusable(false);
            weatherclick.setFocusable(false);
            walletclick.setFocusable(false);
            updateTab.setFocusable(false);
            connectiontab.setFocusable(false);
            stream.setFocusable(false);
            mainbackfilm.setFocusable(false);
            mainbackmusicw.setFocusable(false);
            mainbackrozname.setFocusable(false);
            mainbackmusic.setFocusable(false);
            liketv.setFocusable(false);
            listtv.setFocusable(false);
            hometv.setFocusable(false);
            video_surface_frame_Menu.setFocusable(false);
            drawerlayout.closeDrawers();
        } else if (n == 4) {
            //hide start, hide main page, show web2
            where = 4;
            rl_suport.setVisibility(View.INVISIBLE);
            video_surface_frame_Menu.setVisibility(View.INVISIBLE);
            mainBG1.setVisibility(View.INVISIBLE);
            mainBG2.setVisibility(View.INVISIBLE);
            wb.setVisibility(View.INVISIBLE);
            wb2.setVisibility(View.VISIBLE);
            tvinfoRe.setVisibility(View.INVISIBLE);
            tvlistRe.setVisibility(View.INVISIBLE);

            //focus
            ramz.setFocusable(false);
            esm.setFocusable(false);
            weatherclick.setFocusable(false);
            walletclick.setFocusable(false);
            updateTab.setFocusable(false);
            connectiontab.setFocusable(false);
            stream.setFocusable(false);
            mainbackfilm.setFocusable(false);
            mainbackmusicw.setFocusable(false);
            mainbackrozname.setFocusable(false);
            mainbackmusic.setFocusable(false);
            liketv.setFocusable(false);
            listtv.setFocusable(false);
            hometv.setFocusable(false);
            video_surface_frame_Menu.setFocusable(false);
            drawerlayout.closeDrawers();

        } else if (n == 5) {
            //hide start, hide main page, Hide web2, show Stream

            //video(); ///IDK
            where = 5;
            rl_suport.setVisibility(View.INVISIBLE);
            video_surface_frame_Menu.setVisibility(View.VISIBLE);
            mainBG1.setVisibility(View.INVISIBLE);
            mainBG2.setVisibility(View.INVISIBLE);
            wb.setVisibility(View.INVISIBLE);
            wb2.setVisibility(View.INVISIBLE);
            tvinfoRe.setVisibility(View.VISIBLE);
            tvlistRe.setVisibility(View.VISIBLE);


            //focus
            ramz.setFocusable(false);
            esm.setFocusable(false);
            weatherclick.setFocusable(false);
            walletclick.setFocusable(false);
            updateTab.setFocusable(false);
            connectiontab.setFocusable(false);
            stream.setFocusable(false);
            mainbackfilm.setFocusable(false);
            mainbackmusicw.setFocusable(false);
            mainbackrozname.setFocusable(false);
            mainbackmusic.setFocusable(false);

            liketv.setFocusable(true);
            listtv.setFocusable(true);
            hometv.setFocusable(true);
            video_surface_frame_Menu.setFocusable(false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent events) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:

                stream.hasFocus();

                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {

                        drawerlayout.openDrawer(Gravity.LEFT);
                        drawerlayout.openDrawer(Gravity.RIGHT);

                    }

                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        VideoAdapter.pos = VideoAdapter.pos <= 0 ? VideoAdapter.videoList.size() - 1 : VideoAdapter.pos - 1;
                        final Video video = VideoAdapter.videoList.get(VideoAdapter.pos);
                        Main.TV(video.getUrl(),video.getName());

                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        VideoAdapter.pos = VideoAdapter.pos >= VideoAdapter.videoList.size() - 1 ? 0 : VideoAdapter.pos + 1;
                        final Video video = VideoAdapter.videoList.get(VideoAdapter.pos);
                        Main.TV(video.getUrl(),video.getName());
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (where == 5) {
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                    }
                }
                break;
            case KeyEvent.FLAG_KEEP_TOUCH_MODE:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {

                            drawerlayout.openDrawer(Gravity.LEFT);
                            drawerlayout.openDrawer(Gravity.RIGHT);

                    }
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                break;
        }
        return super.onKeyDown(keyCode, events);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_CENTER:

                stream.hasFocus();

                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {

                            drawerlayout.openDrawer(Gravity.LEFT);
                            drawerlayout.openDrawer(Gravity.RIGHT);

                    }

                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {

                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {

                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                if (where == 5) {
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {
                        audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                    }
                }
                break;
            case KeyEvent.FLAG_KEEP_TOUCH_MODE:
                if (where == 5){
                    if (!drawerlayout.isDrawerOpen(Gravity.LEFT)) {

                            drawerlayout.openDrawer(Gravity.LEFT);
                            drawerlayout.openDrawer(Gravity.RIGHT);

                    }
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                        AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                break;
        }
        return super.dispatchKeyEvent(event);
    }



    //get all data from rest
    public void auth_update() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Main.this);
            String url = "http://188.158.121.78:81/Amvaj/update/json.php?auth=" + Auth;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //VolleyLog.v("Response:%n %s", response.toString(4));

                        String version = "",urlApk = null;
                        if (!response.isNull("auth")) {
                            AuthServer = response.getString("auth");
                        }
                        if (!response.isNull("version")) {
                            version = response.getString("version");
                        }
                        if (!response.isNull("urlApk")) {
                            urlApk = response.getString("urlApk");
                        }

                        if (AuthServer.equals(Auth)){
                            //nothing
                        }else {
                            //access denny

                            one_play_editor.putString("authServer", AuthServer);
                            one_play_editor.apply();

                            Intent uou = new Intent(Main.this, Dialog.class);
                            uou.putExtra("what",2);
                            startActivity(uou);
                            Main.this.finish();
                        }

                        if (version.equals(getString(R.string.version2))){
                            //nothing
                        }else {
                            //update available
                            one_play_editor.putString("update", urlApk);
                            one_play_editor.apply();
                            updateTab.setVisibility(View.VISIBLE);
                        }
                        //MobileAdapter.notifyDataSetChanged();
                        //videoAdapter.setLoading(false);
                    } catch (JSONException e) {
                        Log.e("  error ", String.valueOf(e));
                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", "eerror 2415");
                            // Log.e("VOLLEY", "ERROR");

                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", "b", "b");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };

            requestQueue.add(jsonObjectRequest);

            JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, "http://192.168.40.2/Amvaj/update/jsonlocal.php?auth=" + Auth, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //VolleyLog.v("Response:%n %s", response.toString(4));

                        String version = "",urlApk = null;
                        if (!response.isNull("auth")) {
                            AuthServer = response.getString("auth");
                        }
                        if (!response.isNull("version")) {
                            version = response.getString("version");
                        }
                        if (!response.isNull("urlApk")) {
                            urlApk = response.getString("urlApk");
                        }

                        if (AuthServer.equals(Auth)){
                            //nothing
                        }else {
                            //access denny

                            one_play_editor.putString("authServer", AuthServer);
                            one_play_editor.apply();

                            Intent uou = new Intent(Main.this, Dialog.class);
                            uou.putExtra("what",2);
                            startActivity(uou);
                            Main.this.finish();
                        }

                        if (version.equals(getString(R.string.version2))){
                            //nothing
                        }else {
                            //update available
                            one_play_editor.putString("update", urlApk);
                            one_play_editor.apply();
                            updateTab.setVisibility(View.VISIBLE);
                        }
                        //MobileAdapter.notifyDataSetChanged();
                        //videoAdapter.setLoading(false);
                    } catch (JSONException e) {
                        Log.e("  error ", String.valueOf(e));
                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", "eerror 2485");
                            // Log.e("VOLLEY", "ERROR");
                        }
                    }) {
                @Override
                public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                    HashMap<String, String> params = new HashMap<String, String>();
                    String creds = String.format("%s:%s", "b", "b");
                    String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                    params.put("Authorization", auth);
                    return params;
                }
            };

            requestQueue.add(jsonObjectRequest2);


        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
    }


}
