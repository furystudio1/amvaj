package com.agntic.waves.Music;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.ListVOD.VODvideo;
import com.agntic.waves.ListVOD.adapters.MobileAdapter;
import com.agntic.waves.ListVOD.models.Brand;
import com.agntic.waves.ListVOD.models.Mobile;
import com.agntic.waves.Main;
import com.agntic.waves.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.ui.PlayerControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSchemeDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;
import static java.security.AccessController.getContext;

public class Music extends AppCompatActivity {

    public static ImageView music_previous,music_next;

    public static ImageView ivCover,music_shuffle,music_repeat;
    public static TextView music_title,music_create;

    public static ImageView img_bg_music;

    public static boolean ne = false;

    public static IMusicManager mManager;
    public static MusicManager.Music mCurrentMusic;
    RecyclerView firstRecycleView;
    RecyclerView.Adapter adapter;
    public static Context context;
    public static boolean mSeeking = false;
    public static int mCurrentProgress = 0;


    public static SimpleExoPlayer exoPlayer;
    PlayerControlView player;

    private SeekBar.OnSeekBarChangeListener mSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Log.d("TAG", "onSeekBarProgressChanged: " + progress);
            if (fromUser) {
                mManager.setProgress(mManager.getDuration() * progress / 100);
            }
            mCurrentProgress = progress;
           // Log.e("progress", exoPlayer.getCurrentPosition() + "");


        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            mSeeking = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
       //     Log.d("TAG", "onStopTrackingTouch: " + progress);
            int position = mManager.getDuration() * progress / 100;
            mManager.setProgress(position);
            mSeeking = false;
        }
    };


    private void changePlayState() {
        if (mManager.isPlaying()) {
            Log.d("TAG", "changePlayState: is playing...");
            //music_pause.setImageResource(R.drawable.music_play);
        } else {
            Log.d("TAG", "changePlayState: is not playing...");
            //music_pause.setImageResource(R.drawable.music_pause);
        }
        mManager.playOrPause();
    }

    SharedPreferences one_play_preferences;
    static String IPserver;
    int language,cat;

    public static boolean repeater = false,shuffler = false;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        context = this;
        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        IPserver = one_play_preferences.getString("IPweb", "109.125.130.155:2560");
        language = one_play_preferences.getInt("Language", 1);

        setContentView(R.layout.music);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            cat = extras.getInt("Category");
            //The key argument here must match that used in the other activity
        }


        ivCover = findViewById(R.id.ivCover);
        img_bg_music = findViewById(R.id.img_bg_music);
        music_title = findViewById(R.id.music_title);
        music_create = findViewById(R.id.music_create);
        player = findViewById(R.id.player_control_view);

        music_previous = findViewById(R.id.music_previous);
        //music_previous.setOnClickListener(v -> mManager.playPrevious());
        //music_pause.setOnClickListener(v -> changePlayState());
        music_next = findViewById(R.id.music_next);
        //music_next.setOnClickListener(v -> mManager.playNext());
        music_repeat = findViewById(R.id.music_repeat);
        music_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeater){
                    repeater = false;
                    music_repeat.setImageResource(R.drawable.repeat_off);
                }else {
                    repeater = true;
                    music_repeat.setImageResource(R.drawable.repeat);
                    shuffler = false;
                    music_shuffle.setImageResource(R.drawable.music_shuffle_off);
                }
            }
        });
        music_shuffle = findViewById(R.id.music_shuffle);
        music_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shuffler){
                    shuffler = false;
                    music_shuffle.setImageResource(R.drawable.music_shuffle_off);
                }else {
                    shuffler = true;
                    repeater = false;
                    music_repeat.setImageResource(R.drawable.repeat_off);
                    music_shuffle.setImageResource(R.drawable.music_shuffle_on);
                }
            }
        });

        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        music_title.setTypeface(typeface);
        music_create.setTypeface(typeface);


        /**mp = new MediaPlayer();
        try {
            mp.setDataSource("https://irsv.upmusics.com/99/Homayoun%20Shajarian%20%7C%20Yek%20Nafas%20Arezooye%20To%20(320).mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        mp.prepareAsync();

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // TODO Auto-generated method stub

                mp.start();

            }
        });*/

        firstRecycleView = (RecyclerView) findViewById(R.id.RecycleList_vod_child);
        firstRecycleView.setHasFixedSize(true);
        firstRecycleView.setLayoutManager(
                new LinearLayoutManager(Music.this, LinearLayoutManager.HORIZONTAL,
                        false));

        videolist();


        AudioAttributes audioAttributes = new AudioAttributes.Builder().setUsage(C.USAGE_MEDIA).setContentType(C.CONTENT_TYPE_MUSIC).build();

        exoPlayer = new SimpleExoPlayer.Builder(Music.this).build();
        exoPlayer.setHandleAudioBecomingNoisy(true);
        exoPlayer.setAudioAttributes(audioAttributes,true);
        MusicAdapter.pos = 0;
        exoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {

            }

            @Override
            public void onIsLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == ExoPlayer.STATE_ENDED){
                    Log.e("  error ", "true");
                    //player back ended
                    if (!ne){
                        if (Music.shuffler){

                            final int random = new Random().nextInt(MusicAdapter.mobiles.size());
                            if (MusicAdapter.pos == random){
                                final int random2 = new Random().nextInt(MusicAdapter.mobiles.size());
                                MusicAdapter.pos = random2;
                            }else {
                                MusicAdapter.pos = random;
                            }
                            try{
                                Music.music_title.setText(MusicAdapter.mobiles.get(MusicAdapter.pos).getName());
                                Music.music_create.setText(MusicAdapter.mobiles.get(MusicAdapter.pos).getArtist());
                                Music.exoPlayer.clearMediaItems();
                                MediaItem mediaItem = MediaItem.fromUri(MusicAdapter.mobiles.get(MusicAdapter.pos).getUrl_img());

                                Music.exoPlayer.setMediaItem(mediaItem);
                                Music.exoPlayer.prepare();
                                Music.exoPlayer.setPlayWhenReady(true);

                                if (MusicAdapter.mobiles.get(MusicAdapter.pos).getAuthor() != null) {

                                    RequestOptions options = new RequestOptions()
                                            .centerCrop()
                                            .error(R.drawable.default_artwork);

                                    Glide.with(context)
                                            .asBitmap().load(MusicAdapter.mobiles.get(MusicAdapter.pos).getAuthor())
                                            .apply(options).into(Music.ivCover);

                                } else {
                                    Music.ivCover.setImageResource(R.drawable.default_artwork);
                                }

                                //notifyItemChanged(pos);
                            } catch (Exception e) {
                                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                                e.printStackTrace();
                            }

                        }else if (!Music.repeater){

                            MusicAdapter.pos = MusicAdapter.pos >= MusicAdapter.mobiles.size() - 1 ? 0 : MusicAdapter.pos + 1;

                            Log.e("  error123 ", MusicAdapter.pos +""); /////////////////////////////////////////////////////////////////////////////////
                            Log.e("  error2135 ", MusicAdapter.mobiles.size() +""); /////////////////////////////////////////////////////////////////////////////////
                            try{
                                Music.music_title.setText(MusicAdapter.mobiles.get(MusicAdapter.pos).getName());
                                Music.music_create.setText(MusicAdapter.mobiles.get(MusicAdapter.pos).getArtist());
                                Music.exoPlayer.clearMediaItems();
                                MediaItem mediaItem = MediaItem.fromUri(MusicAdapter.mobiles.get(MusicAdapter.pos).getUrl_img());

                                Music.exoPlayer.setMediaItem(mediaItem);
                                Music.exoPlayer.prepare();
                                Music.exoPlayer.setPlayWhenReady(true);

                                if (MusicAdapter.mobiles.get(MusicAdapter.pos).getAuthor() != null) {

                                    RequestOptions options = new RequestOptions()
                                            .centerCrop()
                                            .error(R.drawable.default_artwork);

                                    Glide.with(context)
                                            .asBitmap().load(MusicAdapter.mobiles.get(MusicAdapter.pos).getAuthor())
                                            .apply(options).into(Music.ivCover);

                                } else {
                                    Music.ivCover.setImageResource(R.drawable.default_artwork);
                                }

                                //notifyItemChanged(pos);

                            } catch (Exception e) {
                                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                                e.printStackTrace();
                            }

                        }else if (Music.repeater){

                            try{
                                Music.music_title.setText(MusicAdapter.mobiles.get(MusicAdapter.pos).getName());
                                Music.music_create.setText(MusicAdapter.mobiles.get(MusicAdapter.pos).getArtist());
                                Music.exoPlayer.clearMediaItems();
                                MediaItem mediaItem = MediaItem.fromUri(MusicAdapter.mobiles.get(MusicAdapter.pos).getUrl_img());

                                Music.exoPlayer.setMediaItem(mediaItem);
                                Music.exoPlayer.prepare();
                                Music.exoPlayer.setPlayWhenReady(true);

                                if (MusicAdapter.mobiles.get(MusicAdapter.pos).getAuthor() != null) {

                                    RequestOptions options = new RequestOptions()
                                            .centerCrop()
                                            .error(R.drawable.default_artwork);

                                    Glide.with(context)
                                            .asBitmap().load(MusicAdapter.mobiles.get(MusicAdapter.pos).getAuthor())
                                            .apply(options).into(Music.ivCover);

                                } else {
                                    Music.ivCover.setImageResource(R.drawable.default_artwork);
                                }

                                //notifyItemChanged(pos);

                            } catch (Exception e) {
                                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                                e.printStackTrace();
                            }

                        }
                    }


                }
            }

            @Override
            public void onPlaybackStateChanged(int playbackState) {

            }

            @Override
            public void onPlayWhenReadyChanged(boolean playWhenReady, int reason) {

            }

            @Override
            public void onSeekBackIncrementChanged(long seekBackIncrementMs) {

            }

            @Override
            public void onSeekForwardIncrementChanged(long seekForwardIncrementMs) {

            }

            @Override
            public void onMaxSeekToPreviousPositionChanged(int maxSeekToPreviousPositionMs) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


        player.setPlayer(exoPlayer);


    }

    @Override
    protected void onStop() {

        if (exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.clearMediaItems();
            adapter = null;
            context = null;
            repeater = false;
            shuffler = false;
        }
        //mp.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        if (exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.clearMediaItems();
            adapter = null;
            context = null;
            repeater = false;
            shuffler = false;
        }
        //mp.stop();
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {

        if (exoPlayer != null){
            exoPlayer.stop();
            exoPlayer.clearMediaItems();
            adapter = null;
            context = null;
            repeater = false;
            shuffler = false;
            List<MusicModels> oneplusMobiles = new ArrayList<MusicModels>();
            oneplusMobiles.clear();
        }
        Intent uou = new Intent(Music.this, MusicList.class);
        startActivity(uou);
        Music.this.finish();

    }


    private class ServiceOnCreateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("TAG", "onReceive: ServiceOnCreate");
            mManager = MusicManager.getInstance();
            initMusicManager(true);
            unregisterReceiver(this);
        }
    }


    public static void initMusicManager(boolean first) {

        mManager.setOnProgressListener(new IMusicManager.OnProgressListener() {
            @Override
            public void onProgress(MusicManager.Music music, int progress, int duration) {
                onProgressChanged(music,progress,duration);
            }
        });

    }


    public static void onProgressChanged(MusicManager.Music music, int progress, int duration) {
        Log.d("TAG", "onProgressChanged: " + progress);
        // 切歌
        if (!music.equals(mCurrentMusic)) {
            mCurrentMusic = music;
            music_title.setText(music.name);
            music_create.setText(music.artist);
            if (music.author != null) {

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.default_artwork);

                Glide.with(context)
                        .asBitmap().load(music.author)
                        .apply(options).into(ivCover);

            } else {
                ivCover.setImageResource(R.drawable.default_artwork);
            }
        }

    }


    public void videolist() {
        try {

            String url = "http://" + IPserver + "/Amvaj/music/json.php";
            //String url = "http://46.100.60.180/vod/music/json.php";

            RequestQueue requestQueue = Volley.newRequestQueue(Music.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //VolleyLog.v("Response:%n %s", response.toString(4));

                        JSONArray students = response.getJSONArray("data");

                        List<MusicModels> oneplusMobiles = new ArrayList<MusicModels>();
                        oneplusMobiles.clear();
                        oneplusMobiles = new ArrayList<>();
                        //oneplusMobiles = null;
                        // looping through All Students
                        for (int i = 0; i < students.length(); i++) {
                            JSONObject c = students.getJSONObject(i);

                            int num = 0;

                            if (!c.isNull("musicCategoryCount")) {
                                num = c.getInt("musicCategoryCount");
                            }

                            if (cat == num){

                                JSONArray students2 = c.getJSONArray("data");

                                for (int i2 = 0; i2 < students2.length(); i2++) {

                                    JSONObject c2 = students2.getJSONObject(i2);

                                    String uri = null;
                                    String name = null;
                                    String artist = null;
                                    String imgUrl = null;

                                    //Farsi
                                    if (!c2.isNull("musicUrl")) {
                                        uri = c2.getString("musicUrl");
                                    }
                                    if (!c2.isNull("imgUrl")) {
                                        imgUrl = c2.getString("imgUrl");
                                    }


                                    if (language == 1){
                                        //Farsi

                                        if (!c2.isNull("name")) {
                                            name = c2.getString("name");
                                        }
                                    }else if (language == 2){
                                        //ENG

                                        if (!c2.isNull("nameENG")) {
                                            name = c2.getString("nameENG");
                                        }
                                    }else if (language == 3){
                                        //Arabic

                                        if (!c2.isNull("nameAR")) {
                                            name = c2.getString("nameAR");
                                        }
                                    }

                                    if (language == 1){
                                        //Farsi

                                        if (!c2.isNull("artist")) {
                                            artist = c2.getString("artist");
                                        }
                                    }else if (language == 2){
                                        //ENG

                                        if (!c2.isNull("artistENG")) {
                                            artist = c2.getString("artistENG");
                                        }
                                    }else if (language == 3){
                                        //Arabic

                                        if (!c2.isNull("artistAR")) {
                                            artist = c2.getString("artistAR");
                                        }
                                    }

                                    if (i2 == 0){
                                        RequestOptions options = new RequestOptions()
                                                .centerCrop()
                                                .error(R.drawable.default_artwork);

                                        Glide.with(context)
                                                .asBitmap().load(imgUrl)
                                                .apply(options).into(Music.ivCover);

                                        MediaItem mediaItem = MediaItem.fromUri(uri);

                                        exoPlayer.setMediaItem(mediaItem);

                                        exoPlayer.prepare();
                                        exoPlayer.setPlayWhenReady(false);

                                        Music.music_title.setText(name);
                                        Music.music_create.setText(artist);
                                    }
                                    ///MusicManager.Music music = new MusicManager.Music(title, uri, artist, author, album, year, cover);

                                    oneplusMobiles.add(new MusicModels(name, uri, artist, imgUrl));
                                }

                            }


                            //musicList.add(music);
                        }
                        adapter = new MusicAdapter(Music.this,oneplusMobiles);
                        firstRecycleView.setAdapter(adapter);

                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        Log.e("  error ", String.valueOf(e));
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


    private List<MusicManager.Music> listMusic() {

        List<MusicManager.Music> musicList = new ArrayList<>();


        String uri = "https://dls.music-fa.com/tagdl/ati/Ehsan%20Khajeamiri%20-%20Dar%20Kenare%20Parvaneha%20(320).mp3";
        String title = "در کنار پروانه ها";
        String artist = "احسان خواجه امیری";
        String author = "https://music-fa.com/wp-content/uploads/2021/08/Ehsan-Khaje-Amiri-Dar-Kenare-Parvaneha-Music-fa.com_.jpg";


            MusicManager.Music music = new MusicManager.Music(title, uri, artist, author);

            musicList.add(music);

        return musicList;
    }


    @SuppressLint("DefaultLocale")
    public static String getTimeString(int millisecond) {
        int totalSecond = millisecond / 1000;
        int minute = totalSecond / 60;
        int second = totalSecond - 60 * minute;
        return String.format("%02d:%02d", minute, second);
    }

}
