package com.agntic.waves.ListVOD;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.MediaController;
import android.widget.Toast;

import com.agntic.waves.Main;
import com.agntic.waves.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;
import org.videolan.libvlc.util.VLCVideoLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class VODdetailVideo extends AppCompatActivity {

    ImageView img_video_detail,img_video_detail_mid,vod_video_player_bg;
    TextView btn_play_video,name_eng_video_vod,name_video_vod,dis_video_vod,name_on_video_duration,firstRecycleViewTitle,dis_video_vod_about;

    String name,dis,nameEng,url_img,url_img_bg,url_video,cat;

    PlayerView player;
    // creating a variable for exoplayer
    SimpleExoPlayer exoPlayer;
    boolean doubleBackToExitPressedOnce = false;
    int where = 1;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (where == 2){
            exoPlayer.stop();
        }
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (where == 1){
            finish();
            super.onBackPressed();
        }else if (where == 2){
        if (doubleBackToExitPressedOnce) {
            try {
                where = 1;
                exoPlayer.stop();
                // Go TO Page Back (Page 1 or MainActivity)
                player.setVisibility(View.GONE);
                vod_video_player_bg.setVisibility(View.GONE);

                btn_play_video.setVisibility(View.VISIBLE);
                btn_play_video.requestFocus();
            } catch (Exception e) {
                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                e.printStackTrace();
            }
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        player.showController();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (where == 2){
            exoPlayer.release();
        }
    }


    SharedPreferences one_play_preferences;
    static SharedPreferences.Editor one_play_editor;

    int language;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        language = one_play_preferences.getInt("Language", 1);

        if (language == 1){

            setContentView(R.layout.video_detail);
        }else if (language == 2){

            setContentView(R.layout.video_detail_eng);
        }else if (language == 3){

            setContentView(R.layout.video_detail);
        }

        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        img_video_detail = findViewById(R.id.img_video_detail);
        img_video_detail_mid = findViewById(R.id.img_video_detail_mid);
        btn_play_video = findViewById(R.id.btn_play_video);
        name_eng_video_vod = findViewById(R.id.name_eng_video_vod);
        name_video_vod = findViewById(R.id.name_video_vod);
        dis_video_vod = findViewById(R.id.dis_video_vod);
        player = findViewById(R.id.video_player_view);
        vod_video_player_bg = findViewById(R.id.vod_video_player_bg);
        dis_video_vod_about = findViewById(R.id.dis_video_vod_about);

        player.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            dis = extras.getString("dis");
            nameEng = extras.getString("nameEng");
            url_img = extras.getString("url_img");
            url_img_bg = extras.getString("url_img_bg");
            url_video = extras.getString("url_video");
            cat = extras.getString("cat");
            //The key argument here must match that used in the other activity
        }


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.b1);

        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(url_img))
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(options)
                .into(img_video_detail_mid);


        Glide.with(this)
                .asBitmap()
                .load(Uri.parse(url_img_bg))
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(options)
                .into(img_video_detail);



       // player.hasFocusable();

        if (language == 1){
            //Farsi
            btn_play_video.setText(R.string.watch);
            name_video_vod.setText(R.string.movie);
            dis_video_vod_about.setText(R.string.aboutMovie);
        }else if (language == 2){
            //Eng
            btn_play_video.setText(R.string.watchEng);
            name_video_vod.setText(R.string.movieEng);
            dis_video_vod_about.setText(R.string.aboutMovieEng);

        }else if (language == 3){
            //AR
            btn_play_video.setText(R.string.watchAR);
            name_video_vod.setText(R.string.movieAR);
            dis_video_vod_about.setText(R.string.aboutMovieAR);

        }


        name_video_vod.setText(name);
        dis_video_vod.setText(dis);
        //long minutes = TimeUnit.MILLISECONDS.toMinutes(exoPlayer.getDuration());
        name_eng_video_vod.setText(cat);
        //Log.e("getDuration", "Error : " + exoPlayer.getDuration() + " " + exoPlayer.getClock() + " " + exoPlayer.getVideoSize() + " " + exoPlayer.getVideoFormat());



        btn_play_video.setFocusable(true);
        btn_play_video.setFocusableInTouchMode(true);
        btn_play_video.requestFocus();

        btn_play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                where = 2;
                //Video Player

                //andExoPlayerView.setVisibility(View.VISIBLE);
               // HashMap<String , String> extraHeaders = new HashMap<>();
                //andExoPlayerView.setSource(url_video, extraHeaders);

                try {

                    // bandwisthmeter is used for
                    // getting default bandwidth
                    //BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

                    // track selector is used to navigate between
                    // video using a default seekbar.
                    //TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

                    // we are adding our track selector to exoplayer.
                    //exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

                    // we are parsing a video url
                    // and parsing its video uri.

                    // we are creating a variable for datasource factory
                    // and setting its user agent as 'exoplayer_view'
                    //DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

                    // we are creating a variable for extractor factory
                    // and setting it to default extractor factory.
                    //ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

                    // we are creating a media source with above variables
                    // and passing our event handler as null,
                    //MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);
                    player.setVisibility(View.VISIBLE);
                    vod_video_player_bg.setVisibility(View.VISIBLE);
                    // Instantiate the player.
// Instantiate the player.
                    exoPlayer = new SimpleExoPlayer.Builder(VODdetailVideo.this).build();
// Attach player to the view.
                    // Build the media item.
                    MediaItem mediaItem = MediaItem.fromUri(url_video);
// Set the media item to be played.
                    exoPlayer.setMediaItem(mediaItem);
// Prepare the player.
                    exoPlayer.prepare();
                    // inside our exoplayer view
                    // we are setting our player
                    player.setPlayer(exoPlayer);
                    // we are preparing our exoplayer
                    // with media source.
                    //exoPlayer.prepare(mediaSource);
                    exoPlayer.prepare();
                    // we are setting our exoplayer
                    // when it is ready.
                    exoPlayer.setPlayWhenReady(true);

                    player.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS);

                    name_on_video_duration.setText(
                            exoPlayer.getDuration() + " min");

                    firstRecycleViewTitle.setText(name);
                    firstRecycleViewTitle.setLayoutParams(
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    name_on_video_duration.setLayoutParams(
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                    btn_play_video.setVisibility(View.GONE);
                    Log.e("getDuration", "Error : " + exoPlayer.getDuration() + " " + exoPlayer.getClock() + " " + exoPlayer.getVideoSize() + " " + exoPlayer.getVideoFormat());
                } catch (Exception e) {
                    // below line is used for
                    // handling our errors.
                    Log.e("TAG", "Error : " + e.toString());
                }

            }
        });

        View view = getLayoutInflater().inflate(R.layout.exo_player_control_view, null);
        firstRecycleViewTitle = (TextView) view.findViewById(R.id.name_on_video);
        name_on_video_duration = (TextView) view.findViewById(R.id.name_on_video_duration);

        firstRecycleViewTitle.setTypeface(typeface);
        btn_play_video.setTypeface(typeface);
        name_eng_video_vod.setTypeface(typeface);
        name_video_vod.setTypeface(typeface);
        dis_video_vod.setTypeface(typeface);
        name_on_video_duration.setTypeface(typeface);


        //extraHeaders.put("foo","bar");

    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent events) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.FLAG_KEEP_TOUCH_MODE:

                if (where == 2){
                    player.showController();
                }
                //esm.hasFocus();
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:

                if (where == 2){
                    if (!player.isControllerVisible()){

                        //Log.e("seek", "Error : " + exoPlayer.getCurrentPosition());
                        exoPlayer.seekTo(exoPlayer.getCurrentPosition() - exoPlayer.getSeekBackIncrement());
                    }

                }
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:

                if (where == 2){
                    if (!player.isControllerVisible()){

                        //Log.e("seek", "Error : " + exoPlayer.getCurrentPosition());
                        exoPlayer.seekTo(exoPlayer.getCurrentPosition() + exoPlayer.getSeekForwardIncrement());
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:

                if (where == 2){
                    if (!player.isControllerVisible()){

                        //Log.e("seek", "Error : " + exoPlayer.getCurrentPosition());
                        exoPlayer.seekTo(exoPlayer.getCurrentPosition() - exoPlayer.getSeekBackIncrement()- exoPlayer.getSeekBackIncrement()- exoPlayer.getSeekBackIncrement()- exoPlayer.getSeekBackIncrement());
                    }
                }
                break;
            case KeyEvent.KEYCODE_DPAD_UP:

                if (where == 2){
                    if (!player.isControllerVisible()){

                        //Log.e("seek", "Error : " + exoPlayer.getCurrentPosition());
                        exoPlayer.seekTo(exoPlayer.getCurrentPosition() + exoPlayer.getSeekForwardIncrement()+ exoPlayer.getSeekForwardIncrement()+ exoPlayer.getSeekForwardIncrement()+ exoPlayer.getSeekForwardIncrement());
                    }
                }
                break;
        }
        return super.onKeyDown(keyCode, events);
    }



}
