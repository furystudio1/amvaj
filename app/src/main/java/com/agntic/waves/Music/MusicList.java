package com.agntic.waves.Music;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.Dialog.Dialog;
import com.agntic.waves.ListVOD.VODdetailVideo;
import com.agntic.waves.ListVOD.VODvideo;
import com.agntic.waves.Main;
import com.agntic.waves.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.felipecsl.asymmetricgridview.library.Utils;
import com.felipecsl.asymmetricgridview.library.model.AsymmetricItem;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridView;
import com.felipecsl.asymmetricgridview.library.widget.AsymmetricGridViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MusicList extends AppCompatActivity {

    CardView cardlist1,cardlist2,cardlist3,cardlist4,cardlist5,cardlist6,cardlist7,cardlist8,cardlist9,cardlist10,cardlist11,cardlist12
            ,cardlist13,cardlist14,cardlist15,cardlist16,cardlist17,cardlist18,cardlist19,cardlist20;

    ImageView img_cat_item_music_1,img_cat_item_music_2,img_cat_item_music_3,img_cat_item_music_4,img_cat_item_music_5,img_cat_item_music_6,img_cat_item_music_7,img_cat_item_music_8,img_cat_item_music_9
            ,img_cat_item_music_10,img_cat_item_music_11,img_cat_item_music_12,img_cat_item_music_13,img_cat_item_music_14,img_cat_item_music_15,img_cat_item_music_16,img_cat_item_music_17,img_cat_item_music_18
            ,img_cat_item_music_19,img_cat_item_music_20,bg_list_music;

    TextView txt_cat_item_music_1,txt_cat_item_music_2,txt_cat_item_music_3,txt_cat_item_music_4,txt_cat_item_music_5,txt_cat_item_music_6,txt_cat_item_music_7,txt_cat_item_music_8,txt_cat_item_music_9
            ,txt_cat_item_music_10,txt_cat_item_music_11,txt_cat_item_music_12,txt_cat_item_music_13,txt_cat_item_music_14,txt_cat_item_music_15,txt_cat_item_music_16,txt_cat_item_music_17,txt_cat_item_music_18
            ,txt_cat_item_music_19,txt_cat_item_music_20,cate_music;

    SharedPreferences one_play_preferences;
    static String IPserver;
    int language;
    String urlImg1,urlImg2,urlImg3,urlImg4,urlImg5,urlImg6,urlImg7,urlImg8,urlImg9,urlImg10,urlImg11,urlImg12,urlImg13,urlImg14,urlImg15,urlImg16,urlImg17,urlImg18,urlImg19,urlImg20;


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


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        IPserver = one_play_preferences.getString("IPweb", "109.125.130.155:2560");
        language = one_play_preferences.getInt("Language", 1);

        setContentView(R.layout.listmusic);

        bg_list_music = findViewById(R.id.bg_list_music);
        cate_music = findViewById(R.id.cate_music);
        cardlist1 = findViewById(R.id.cardlist1);
        cardlist2 = findViewById(R.id.cardlist2);
        cardlist3 = findViewById(R.id.cardlist3);
        cardlist4 = findViewById(R.id.cardlist4);
        cardlist5 = findViewById(R.id.cardlist5);
        cardlist6 = findViewById(R.id.cardlist6);
        cardlist7 = findViewById(R.id.cardlist7);
        cardlist8 = findViewById(R.id.cardlist8);
        cardlist9 = findViewById(R.id.cardlist9);
        cardlist10 = findViewById(R.id.cardlist10);
        cardlist11 = findViewById(R.id.cardlist11);
        cardlist12 = findViewById(R.id.cardlist12);
        cardlist13 = findViewById(R.id.cardlist13);
        cardlist14 = findViewById(R.id.cardlist14);
        cardlist15 = findViewById(R.id.cardlist15);
        cardlist16 = findViewById(R.id.cardlist16);
        cardlist17 = findViewById(R.id.cardlist17);
        cardlist18 = findViewById(R.id.cardlist18);
        cardlist19 = findViewById(R.id.cardlist19);
        cardlist20 = findViewById(R.id.cardlist20);

        img_cat_item_music_1  = findViewById(R.id.img_cat_item_music_1);
        img_cat_item_music_2  = findViewById(R.id.img_cat_item_music_2);
        img_cat_item_music_3  = findViewById(R.id.img_cat_item_music_3);
        img_cat_item_music_4  = findViewById(R.id.img_cat_item_music_4);
        img_cat_item_music_5  = findViewById(R.id.img_cat_item_music_5);
        img_cat_item_music_6  = findViewById(R.id.img_cat_item_music_6);
        img_cat_item_music_7  = findViewById(R.id.img_cat_item_music_7);
        img_cat_item_music_8  = findViewById(R.id.img_cat_item_music_8);
        img_cat_item_music_9  = findViewById(R.id.img_cat_item_music_9);
        img_cat_item_music_10 = findViewById(R.id.img_cat_item_music_10);
        img_cat_item_music_11 = findViewById(R.id.img_cat_item_music_11);
        img_cat_item_music_12 = findViewById(R.id.img_cat_item_music_12);
        img_cat_item_music_13 = findViewById(R.id.img_cat_item_music_13);
        img_cat_item_music_14 = findViewById(R.id.img_cat_item_music_14);
        img_cat_item_music_15 = findViewById(R.id.img_cat_item_music_15);
        img_cat_item_music_16 = findViewById(R.id.img_cat_item_music_16);
        img_cat_item_music_17 = findViewById(R.id.img_cat_item_music_17);
        img_cat_item_music_18 = findViewById(R.id.img_cat_item_music_18);
        img_cat_item_music_19 = findViewById(R.id.img_cat_item_music_19);
        img_cat_item_music_20 = findViewById(R.id.img_cat_item_music_20);

        txt_cat_item_music_1  = findViewById(R.id.txt_cat_item_music_1);
        txt_cat_item_music_2 = findViewById(R.id.txt_cat_item_music_2);
        txt_cat_item_music_3 = findViewById(R.id.txt_cat_item_music_3);
        txt_cat_item_music_4 = findViewById(R.id.txt_cat_item_music_4);
        txt_cat_item_music_5 = findViewById(R.id.txt_cat_item_music_5);
        txt_cat_item_music_6 = findViewById(R.id.txt_cat_item_music_6);
        txt_cat_item_music_7 = findViewById(R.id.txt_cat_item_music_7);
        txt_cat_item_music_8 = findViewById(R.id.txt_cat_item_music_8);
        txt_cat_item_music_9 = findViewById(R.id.txt_cat_item_music_9);
        txt_cat_item_music_10 = findViewById(R.id.txt_cat_item_music_10);
        txt_cat_item_music_11 = findViewById(R.id.txt_cat_item_music_11);
        txt_cat_item_music_12 = findViewById(R.id.txt_cat_item_music_12);
        txt_cat_item_music_13 = findViewById(R.id.txt_cat_item_music_13);
        txt_cat_item_music_14 = findViewById(R.id.txt_cat_item_music_14);
        txt_cat_item_music_15 = findViewById(R.id.txt_cat_item_music_15);
        txt_cat_item_music_16 = findViewById(R.id.txt_cat_item_music_16);
        txt_cat_item_music_17 = findViewById(R.id.txt_cat_item_music_17);
        txt_cat_item_music_18 = findViewById(R.id.txt_cat_item_music_18);
        txt_cat_item_music_19 = findViewById(R.id.txt_cat_item_music_19);
        txt_cat_item_music_20 = findViewById(R.id.txt_cat_item_music_20);


        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        txt_cat_item_music_1 .setTypeface(typeface);
        txt_cat_item_music_2 .setTypeface(typeface);
        txt_cat_item_music_3 .setTypeface(typeface);
        txt_cat_item_music_4 .setTypeface(typeface);
        txt_cat_item_music_5 .setTypeface(typeface);
        txt_cat_item_music_6 .setTypeface(typeface);
        txt_cat_item_music_7 .setTypeface(typeface);
        txt_cat_item_music_8 .setTypeface(typeface);
        txt_cat_item_music_9 .setTypeface(typeface);
        txt_cat_item_music_10.setTypeface(typeface);
        txt_cat_item_music_11.setTypeface(typeface);
        txt_cat_item_music_12.setTypeface(typeface);
        txt_cat_item_music_13.setTypeface(typeface);
        txt_cat_item_music_14.setTypeface(typeface);
        txt_cat_item_music_15.setTypeface(typeface);
        txt_cat_item_music_16.setTypeface(typeface);
        txt_cat_item_music_17.setTypeface(typeface);
        txt_cat_item_music_18.setTypeface(typeface);
        txt_cat_item_music_19.setTypeface(typeface);
        txt_cat_item_music_20.setTypeface(typeface);
        cate_music.setTypeface(typeface);

        cardlist1 .setVisibility(View.INVISIBLE);
        cardlist2 .setVisibility(View.INVISIBLE);
        cardlist3 .setVisibility(View.INVISIBLE);
        cardlist4 .setVisibility(View.INVISIBLE);
        cardlist5 .setVisibility(View.INVISIBLE);
        cardlist6 .setVisibility(View.INVISIBLE);
        cardlist7 .setVisibility(View.INVISIBLE);
        cardlist8 .setVisibility(View.INVISIBLE);
        cardlist9 .setVisibility(View.INVISIBLE);
        cardlist10.setVisibility(View.INVISIBLE);
        cardlist11.setVisibility(View.INVISIBLE);
        cardlist12.setVisibility(View.INVISIBLE);
        cardlist13.setVisibility(View.INVISIBLE);
        cardlist14.setVisibility(View.INVISIBLE);
        cardlist15.setVisibility(View.INVISIBLE);
        cardlist16.setVisibility(View.INVISIBLE);
        cardlist17.setVisibility(View.INVISIBLE);
        cardlist18.setVisibility(View.INVISIBLE);
        cardlist19.setVisibility(View.INVISIBLE);
        cardlist20.setVisibility(View.INVISIBLE);


        if (language == 1){
            //Farsi
            cate_music.setText(R.string.catMusic);

        }else if (language == 2){
            //ENG

            cate_music.setText(R.string.catMusicENG);

        }else if (language == 3){
            //Arabic

            cate_music.setText(R.string.catMusicAR);

        }


        videolist();


        cardlist1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg1)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_1.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_1.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg2)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_2.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_2.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg3)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_3.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_3.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist4.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg4)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_4.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_4.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist5.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg5)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_5.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_5.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist6.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg6)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_6.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_6.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist7.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg7)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_7.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_7.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist8.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg8)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_8.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_8.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist9.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg9)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_9.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_9.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist10.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg10)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_10.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_10.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist11.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg11)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_11.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_11.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist12.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg12)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_12.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_12.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist13.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg13)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_13.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_13.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist14.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg14)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_14.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_14.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist15.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg15)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_15.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_15.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist16.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg16)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_16.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_16.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist17.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg17)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_17.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_17.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist18.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg18)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_18.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_18.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist19.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg19)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_19.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_19.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });

        cardlist20.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus){

                    Glide.with(MusicList.this)
                            .load(urlImg20)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(bg_list_music);

                    v.animate().scaleY(1.2f).scaleX(1.2f).start();
                    txt_cat_item_music_20.setBackgroundResource(R.color.color_listmusic_on);
                }else {
                    v.animate().scaleY(1f).scaleX(1f).start();
                    txt_cat_item_music_20.setBackgroundResource(R.color.color_listmusic_off);
                }

            }
        });



        //Click
        cardlist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",1);
                startActivity(i);
                finish();

            }
        });

        cardlist2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",2);
                startActivity(i);
                finish();

            }
        });

        cardlist3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",3);
                startActivity(i);
                finish();

            }
        });

        cardlist4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",4);
                startActivity(i);
                finish();

            }
        });

        cardlist5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",5);
                startActivity(i);
                finish();

            }
        });

        cardlist6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",6);
                startActivity(i);
                finish();

            }
        });

        cardlist7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",7);
                startActivity(i);
                finish();

            }
        });

        cardlist8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",8);
                startActivity(i);
                finish();

            }
        });

        cardlist9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",9);
                startActivity(i);
                finish();

            }
        });

        cardlist10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",10);
                startActivity(i);
                finish();

            }
        });

        cardlist11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",11);
                startActivity(i);
                finish();

            }
        });

        cardlist12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",12);
                startActivity(i);
                finish();

            }
        });

        cardlist13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",13);
                startActivity(i);
                finish();

            }
        });

        cardlist14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",14);
                startActivity(i);
                finish();

            }
        });

        cardlist15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",15);
                startActivity(i);
                finish();

            }
        });

        cardlist16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",16);
                startActivity(i);
                finish();

            }
        });

        cardlist17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",17);
                startActivity(i);
                finish();

            }
        });

        cardlist18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",18);
                startActivity(i);
                finish();

            }
        });

        cardlist19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",19);
                startActivity(i);
                finish();

            }
        });

        cardlist20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MusicList.this, Music.class);
                i.putExtra("Category",20);
                startActivity(i);
                finish();

            }
        });

    }



    public void videolist() {
        try {

            String url = "http://" + IPserver + "/Amvaj/music/json.php";
            //String url = "http://46.100.60.180/vod/music/json.php";

            RequestQueue requestQueue = Volley.newRequestQueue(MusicList.this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //VolleyLog.v("Response:%n %s", response.toString(4));

                        JSONArray students = response.getJSONArray("data");

                        // looping through All Students
                        for (int i = 0; i < students.length(); i++) {
                            JSONObject c = students.getJSONObject(i);

                            String uri = null;
                            String name = null;
                            int CategoryCount = 0;

                            //Farsi
                            if (!c.isNull("musicCategoryImg")) {
                                uri = c.getString("musicCategoryImg");
                            }
                            if (!c.isNull("musicCategoryCount")) {
                                CategoryCount = c.getInt("musicCategoryCount");
                            }

                            if (language == 1){
                                //Farsi

                                if (!c.isNull("musicCategory")) {
                                    name = c.getString("musicCategory");
                                }
                            }else if (language == 2){
                                //ENG

                                if (!c.isNull("musicCategoryENG")) {
                                    name = c.getString("musicCategoryENG");
                                }
                            }else if (language == 3){
                                //Arabic

                                if (!c.isNull("musicCategoryAR")) {
                                    name = c.getString("musicCategoryAR");
                                }
                            }

                            if (CategoryCount == 1){

                                cardlist1.setVisibility(View.VISIBLE);
                                txt_cat_item_music_1.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_1);

                                urlImg1 = uri;

                            }else if (CategoryCount == 2){

                                cardlist2.setVisibility(View.VISIBLE);
                                txt_cat_item_music_2.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_2);

                                urlImg2 = uri;

                            }else if (CategoryCount == 3){

                                cardlist3.setVisibility(View.VISIBLE);
                                txt_cat_item_music_3.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_3);

                                urlImg3 = uri;

                            }else if (CategoryCount == 4){

                                cardlist4.setVisibility(View.VISIBLE);
                                txt_cat_item_music_4.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_4);

                                urlImg4 = uri;

                            }else if (CategoryCount == 5){

                                cardlist5.setVisibility(View.VISIBLE);
                                txt_cat_item_music_5.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_5);

                                urlImg5 = uri;

                            }else if (CategoryCount == 6){

                                cardlist6.setVisibility(View.VISIBLE);
                                txt_cat_item_music_6.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_6);

                                urlImg6 = uri;

                            }else if (CategoryCount == 7){

                                cardlist7.setVisibility(View.VISIBLE);
                                txt_cat_item_music_7.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_7);

                                urlImg7 = uri;

                            }else if (CategoryCount == 8){

                                cardlist8.setVisibility(View.VISIBLE);
                                txt_cat_item_music_8.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_8);

                                urlImg8 = uri;

                            }else if (CategoryCount == 9){

                                cardlist9.setVisibility(View.VISIBLE);
                                txt_cat_item_music_9.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_9);

                                urlImg9 = uri;

                            }else if (CategoryCount == 10){

                                cardlist10.setVisibility(View.VISIBLE);
                                txt_cat_item_music_10.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_10);

                                urlImg10 = uri;

                            }else if (CategoryCount == 11){

                                cardlist11.setVisibility(View.VISIBLE);
                                txt_cat_item_music_11.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_11);

                                urlImg11 = uri;

                            }else if (CategoryCount == 12){

                                cardlist12.setVisibility(View.VISIBLE);
                                txt_cat_item_music_12.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_12);

                                urlImg12 = uri;

                            }else if (CategoryCount == 13){

                                cardlist13.setVisibility(View.VISIBLE);
                                txt_cat_item_music_13.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_13);

                                urlImg13 = uri;

                            }else if (CategoryCount == 14){

                                cardlist14.setVisibility(View.VISIBLE);
                                txt_cat_item_music_14.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_14);

                                urlImg14 = uri;

                            }else if (CategoryCount == 15){

                                cardlist15.setVisibility(View.VISIBLE);
                                txt_cat_item_music_15.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_15);

                                urlImg15 = uri;

                            }else if (CategoryCount == 16){

                                cardlist16.setVisibility(View.VISIBLE);
                                txt_cat_item_music_16.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_16);

                                urlImg16 = uri;

                            }else if (CategoryCount == 17){

                                cardlist17.setVisibility(View.VISIBLE);
                                txt_cat_item_music_17.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_17);

                                urlImg17 = uri;

                            }else if (CategoryCount == 18){

                                cardlist18.setVisibility(View.VISIBLE);
                                txt_cat_item_music_18.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_18);

                                urlImg18 = uri;

                            }else if (CategoryCount == 19){

                                cardlist19.setVisibility(View.VISIBLE);
                                txt_cat_item_music_19.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_19);

                                urlImg19 = uri;

                            }else if (CategoryCount == 20){

                                cardlist20.setVisibility(View.VISIBLE);
                                txt_cat_item_music_20.setText(name);

                                RequestOptions options = new RequestOptions()
                                        .centerCrop()
                                        .error(R.drawable.default_artwork);

                                Glide.with(MusicList.this)
                                        .asBitmap().load(uri)
                                        .apply(options).into(img_cat_item_music_20);

                                urlImg20 = uri;

                            }

                            ///MusicManager.Music music = new MusicManager.Music(title, uri, artist, author, album, year, cover);

                            //musicList.add(music);
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
                            Log.e("  All Json loge ", "eerror");

                            Intent uou = new Intent(MusicList.this, Dialog.class);
                            uou.putExtra("what",6);
                            startActivity(uou);
                            MusicList.this.finish();
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



    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {

        Intent uou = new Intent(MusicList.this, Main.class);
        uou.putExtra("from",1);
        startActivity(uou);
        MusicList.this.finish();

        super.onBackPressed();
    }
}
