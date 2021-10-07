package com.agntic.waves;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.Model.Video;
import com.agntic.waves.Model.VideoAdapter;
import com.agntic.waves.db.DatabaseManager;
import com.agntic.waves.db.Person;
import com.agntic.waves.widget.Clock;
import com.agntic.waves.widget.OnClockTickListner;
import com.agntic.waves.widget.Utility;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.pnikosis.materialishprogress.ProgressWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.interfaces.IVLCVout;
import org.videolan.libvlc.util.VLCVideoLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stream extends AppCompatActivity implements
        IVLCVout.Callback {
    private static String SAMPLE_URL = "";
    //private static final String SAMPLE_URL = "http://b:b@192.168.2.57:9981/stream/channel/5125412c26972527dc4742e7576773a9";
    //    private static final String SAMPLE_URL1 = "http://192.168.1.203:80/acer.mov";
    private static final int SURFACE_BEST_FIT = 0;
    private static int CURRENT_SIZE = SURFACE_BEST_FIT;
    private static int likenumber;
    private static String lastchannelset;


    private VLCVideoLayout mVideoLayout = null;

    private static LibVLC mLibVLC = null;
    private static MediaPlayer mMediaPlayer = null;
    //    private MediaPlayer mMediaPlayer2 = null;
    private int mVideoHeight = 0;
    private int mVideoWidth = 0;
    private int mVideoVisibleHeight = 0;
    private int mVideoVisibleWidth = 0;
    private int mVideoSarNum = 0;
    private int mVideoSarDen = 0;
    String ID = "f8ec204b51ce9a90ce6cdd0df67ea139";

    private List<Video> videos = new ArrayList<>();
    private RecyclerView recyclerView;
    private VideoAdapter videoAdapter;

    //set Loading
    int numberLoadItem = 0;
    int numberLoad = 0;

    DrawerLayout drawerlayout;
    public static Activity fa;

    // Get Save
    SharedPreferences one_play_preferences;
    static SharedPreferences.Editor one_play_editor;
    static String IPserver;
    static String username;
    static String password;
    static String lastchannel;
    String lastchannelname, timemin;
    static String pID;
    ImageView hometv;
    static ImageView liketv;
    ImageView listtv;
    FrameLayout tvclick;
    static TextView nametv;
    static TextView toplist;
    int cli = 0;

    //set Loading
    ProgressWheel progress_wheel;

    boolean doubleBackToExitPressedOnce = false;

    static DatabaseManager dbm;

    static int likeOk = 0;

    Clock c;

    boolean favoritelist;

    TextView clocktv, datetv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_listtv_livetv);

        fa = this;

        try {
            dbm = new DatabaseManager(this);
            likenumber = dbm.personCount();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            // Get Save
            one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
            one_play_editor = one_play_preferences.edit();
            IPserver = one_play_preferences.getString("IPserver", "109.125.130.155:2560");
            username = one_play_preferences.getString("username", "b");
            password = one_play_preferences.getString("password", "b");
            lastchannel = one_play_preferences.getString("lastchannel", "a534a6de6d60eff9a0254e8c933d7ba1");
            lastchannelname = one_play_preferences.getString("lastchannelname", "RTV");
            favoritelist = one_play_preferences.getBoolean("favoritelist", false);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
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
            tvclick = (FrameLayout) findViewById(R.id.video_surface_frame);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }


        try {
            //Show Loading
            progress_wheel.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(Stream.this));
            videoAdapter = new VideoAdapter(videos, Stream.this, recyclerView);
            recyclerView.setAdapter(videoAdapter);
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
                progress_wheel.setVisibility(View.GONE);

                toplist.setText("شبکه های مورد علاقه");
            } else {
                video(); ///IDK
                toplist.setText("تمام شبکه ها");
            }
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            nametv.setText(lastchannelname);

            //Get file font
            Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

            //Set Font
            nametv.setTypeface(typeface);
            clocktv.setTypeface(typeface);
            toplist.setTypeface(typeface);
            datetv.setTypeface(typeface);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            Utility util = new Utility();
            String ywd = util.getCurrentShamsidate();
            datetv.setText(setPersianNumbers(ywd));
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            //load Image
            Glide.with(this).load(getImage("home"))
                    .thumbnail(0.5f).into(hometv);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        //On Click
        hometv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back
                try {
                    mMediaPlayer.stop();
                    mMediaPlayer.detachViews();
                    c.StopTick();
                    // Go TO Page Back (Page 1 or MainActivity)
                    Intent uou = new Intent(Stream.this, Main.class);
                    startActivity(uou);
                    Stream.this.finish();
                } catch (Exception e) {
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
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
                            Toast.makeText(Stream.this, "در حذف اطلاعات اشکالی وجود دارد", Toast.LENGTH_LONG).show();
                        }

                        likenumber = dbm.personCount();
                    }


                    int sCount = dbm.personCount();


                } catch (Exception e) {
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }
            }
        });

        listtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back
                try {

                    if (favoritelist) {
                        toplist.setText("تمام شبکه ها");
                        videos.clear();
                        progress_wheel.setVisibility(View.VISIBLE);
                        video(); ///IDK
                        favoritelist = false;

                        one_play_editor.putBoolean("favoritelist", favoritelist);
                        one_play_editor.apply();

                    } else {
                        toplist.setText("شبکه های مورد علاقه");
                        if (likenumber == 0) {
                            Toast.makeText(Stream.this, "لیست شبکه های موردعلاقه خالی است", Toast.LENGTH_LONG).show();
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
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }
            }
        });


        tvclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }
            }
        });

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
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
        /**key1.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        ID = "37369603723b11ed4b1fd378401694b8";
        SAMPLE_URL = "http://b:b@192.168.2.57:9981/stream/channel/"+ID;
        Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
        media.addOption(":network-caching=150");
        media.addOption(":clock-jitter=0");
        media.addOption(":clock-synchro=0");
        media.addOption(":rtsp-timeout=300");
        //        Media media1 = new Media(mLibVLC, Uri.parse(SAMPLE_URL1));
        mMediaPlayer.setMedia(media);
        //        mMediaPlayer2.setMedia(media1);
        media.release();
        mMediaPlayer.play();
        }
        });
         key2.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        ID = "a534a6de6d60eff9a0254e8c933d7ba1";
        SAMPLE_URL = "http://b:b@192.168.2.57:9981/stream/channel/"+ID;
        Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
        media.addOption(":network-caching=150");
        media.addOption(":clock-jitter=0");
        media.addOption(":clock-synchro=0");
        media.addOption(":rtsp-timeout=300");
        //        Media media1 = new Media(mLibVLC, Uri.parse(SAMPLE_URL1));
        mMediaPlayer.setMedia(media);
        //        mMediaPlayer2.setMedia(media1);
        media.release();
        mMediaPlayer.play();
        }
        });
         key3.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        ID = "f8ec204b51ce9a90ce6cdd0df67ea139";
        SAMPLE_URL = "http://b:b@192.168.2.57:9981/stream/channel/"+ID;
        Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
        media.addOption(":network-caching=150");
        media.addOption(":clock-jitter=0");
        media.addOption(":clock-synchro=0");
        media.addOption(":rtsp-timeout=300");
        //        Media media1 = new Media(mLibVLC, Uri.parse(SAMPLE_URL1));
        mMediaPlayer.setMedia(media);
        //        mMediaPlayer2.setMedia(media1);
        media.release();
        mMediaPlayer.play();
        }
        });**/

        try {
            SAMPLE_URL = "http://" + username + ":" + password + "@" + IPserver + "/stream/channel/" + lastchannel;
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
        try {
            final ArrayList<String> args = new ArrayList<>();
            args.add("--vout=android-display");
            //args.add("--no-drop-late-frames");
            args.add("--no-skip-frames");
            args.add("--avcodec-fast");
            args.add("--network-caching=3000");
            args.add("--audio-time-stretch"); // time stretching
            //args.add("--h264-fps=30"); // time stretching
            args.add("--rtsp-tcp");
            args.add("-vvv");

            mLibVLC = new LibVLC(this, args);
            mMediaPlayer = new MediaPlayer(mLibVLC);
//        mMediaPlayer2 = new MediaPlayer(mLibVLC);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            mMediaPlayer.attachViews(mVideoLayout, null, false, false);
//        mVideoSurfaceFrame1 = (FrameLayout) findViewById(R.id.video_surface_frame1);
//        mVideoSurface1 = (SurfaceView) findViewById(R.id.video_surface1);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        try {
            Media media = new Media(mLibVLC, Uri.parse(SAMPLE_URL));
            media.setHWDecoderEnabled(true, false);       //new
            //media.addOption(":network-caching=3000");
            media.addOption(":clock-jitter=0");
            media.addOption(":clock-synchro=0");       //new
            //media.addOption(":sout-record-dst-prefix=yylpre.mp4");
            media.addOption(":network-caching=3000");
            //media.addOption(":rtsp-frame-buffer-size=100000");
            media.addOption(":rtsp-tcp");
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
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
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


        c = new Clock(this, 0);
        c.AddClockTickListner(new OnClockTickListner() {

            @Override
            public void OnSecondTick(Time currentTime) {
                //Log.e("Tick Test per Second", DateFormat.format("h:mm:ss aa ", currentTime.toMillis(true)).toString());
                clocktv.setText(setPersianNumbers(DateFormat.format("h : mm", currentTime.toMillis(true)).toString()));
            }

            @Override
            public void OnMinuteTick(Time currentTime) {
                //Log.e("Tick Test per Minute",DateFormat.format("h:mm aa", currentTime.toMillis(true)).toString());
            }
        });

    }

    public int getImage(String imageName) {

        int drawableResourceId = this.getResources().getIdentifier(imageName, "drawable", this.getPackageName());

        return drawableResourceId;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mMediaPlayer.release();
            mLibVLC.release();
            c.StopTick();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mMediaPlayer.stop();
            mMediaPlayer.detachViews();
            c.StopTick();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            try {
                mMediaPlayer.stop();
                mMediaPlayer.detachViews();
                c.StopTick();
                // Go TO Page Back (Page 1 or MainActivity)
                Intent uou = new Intent(Stream.this, Main.class);
                startActivity(uou);
                Stream.this.finish();
            } catch (Exception e) {
                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                e.printStackTrace();
            }
            return;
        }

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
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);

    }


    //get all data from rest
    public void video() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Stream.this);
            String url = "http://" + IPserver + "/api/channel/grid";
            //String url = "http://109.125.130.155:2560//api/channel/grid";

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
                        progress_wheel.setVisibility(View.GONE);
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
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
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
            media.setHWDecoderEnabled(true, false);       //new
            media.addOption(":clock-jitter=0");
            media.addOption(":clock-synchro=0");       //new
            media.addOption(":network-caching=3000");
            media.addOption(":rtsp-tcp");
            mMediaPlayer.setMedia(media);
            media.release();
            mMediaPlayer.play();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }
        try {
            nametv.setText(name);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }


        try {
            one_play_editor.putString("lastchannel", uuid);
            one_play_editor.putString("lastchannelname", name);
            one_play_editor.apply();
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        favorite();

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

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {

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
                .replace("9", "۹");
    }

}