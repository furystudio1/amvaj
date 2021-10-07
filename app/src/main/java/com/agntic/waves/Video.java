package com.agntic.waves;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.agntic.waves.Model.TestAdapter;
import com.agntic.waves.Model.VODItemList;
import com.agntic.waves.Model.VideoAdapter;
import com.agntic.waves.Model.VideoItemList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.septenary.widget.HListView;
import cn.septenary.widget.ViewHolder;

public class Video extends Activity {
    ImageView background_video;

    private List<com.agntic.waves.Model.VideoItemList> videos = new ArrayList<>();
    private VODItemList videoAdapter;

    SharedPreferences one_play_preferences;
    static String IPserver;
    int AllCat;
    List<VideoItemList> videoList;
    VideoItemList video;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video);
        background_video = findViewById(R.id.background_video);


        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        IPserver = one_play_preferences.getString("IPweb", "109.125.130.155:2560");

        //Picasso.get().load("https://s19.picofile.com/file/8439380626/z9a.jpg").fit().centerCrop().placeholder(R.drawable.main_bg).error(R.drawable.banner2).into(background_video);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.main_bg);
        Glide.with(this).load("https://i.pinimg.com/originals/0d/83/a7/0d83a78aa64ff5388ce7f5128eea75c7.jpg").apply(options).into(background_video);
        //Glide.with(this).load("https://raygan.weblogtop.com/wp-content/uploads/sites/392/2021/08/Zakhme-Kari-11.jpg").into(background_video);
        listCN();
    }

    public void init() {
        HListView video_list_1 = (HListView) findViewById(R.id.video_list_1);
        HListView video_list_2 = (HListView) findViewById(R.id.video_list_2);
        HListView video_list_3 = (HListView) findViewById(R.id.video_list_3);
        HListView video_list_4 = (HListView) findViewById(R.id.video_list_4);
        HListView video_list_5 = (HListView) findViewById(R.id.video_list_5);
        HListView video_list_6 = (HListView) findViewById(R.id.video_list_6);
        HListView video_list_7 = (HListView) findViewById(R.id.video_list_7);
        HListView video_list_8 = (HListView) findViewById(R.id.video_list_8);
        HListView video_list_9 = (HListView) findViewById(R.id.video_list_9);
        HListView video_list_10 = (HListView) findViewById(R.id.video_list_10);

        if (AllCat == 1){

            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < videoList.size(); i++){
                        video = videoList.get(i);
                        //if (video.getPosition() == position){
                            //item find

                        //}
                    }
                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    for (int i = 0; i < videoList.size(); i++){
                        video = videoList.get(i);
                        //if (video.getPosition() == position){
                            //item find

                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .error(R.drawable.main_bg);
                            Glide.with(Video.this).load(video.getUrl_img()).apply(options).into(background_video);

                        //}
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setVisibility(View.GONE);

            video_list_3.setVisibility(View.GONE);

            video_list_4.setVisibility(View.GONE);

            video_list_5.setVisibility(View.GONE);

            video_list_6.setVisibility(View.GONE);

            video_list_7.setVisibility(View.GONE);

            video_list_8.setVisibility(View.GONE);

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 2){

            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setVisibility(View.GONE);

            video_list_4.setVisibility(View.GONE);

            video_list_5.setVisibility(View.GONE);

            video_list_6.setVisibility(View.GONE);

            video_list_7.setVisibility(View.GONE);

            video_list_8.setVisibility(View.GONE);

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 3){


            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setVisibility(View.GONE);

            video_list_5.setVisibility(View.GONE);

            video_list_6.setVisibility(View.GONE);

            video_list_7.setVisibility(View.GONE);

            video_list_8.setVisibility(View.GONE);

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 4){


            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,4);
            video_list_1.setAdapter(videoAdapter);
            video_list_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_5.setVisibility(View.GONE);

            video_list_6.setVisibility(View.GONE);

            video_list_7.setVisibility(View.GONE);

            video_list_8.setVisibility(View.GONE);

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 5){


            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,4);
            video_list_1.setAdapter(videoAdapter);
            video_list_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_5.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,5);
            video_list_1.setAdapter(videoAdapter);
            video_list_5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_6.setVisibility(View.GONE);

            video_list_7.setVisibility(View.GONE);

            video_list_8.setVisibility(View.GONE);

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 6){


            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,4);
            video_list_1.setAdapter(videoAdapter);
            video_list_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_5.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,5);
            video_list_1.setAdapter(videoAdapter);
            video_list_5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_6.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,6);
            video_list_1.setAdapter(videoAdapter);
            video_list_6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_7.setVisibility(View.GONE);

            video_list_8.setVisibility(View.GONE);

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 7){


            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,4);
            video_list_1.setAdapter(videoAdapter);
            video_list_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_5.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,5);
            video_list_1.setAdapter(videoAdapter);
            video_list_5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_6.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,6);
            video_list_1.setAdapter(videoAdapter);
            video_list_6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_7.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,7);
            video_list_1.setAdapter(videoAdapter);
            video_list_7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_8.setVisibility(View.GONE);

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 8){

            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,4);
            video_list_1.setAdapter(videoAdapter);
            video_list_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_5.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,5);
            video_list_1.setAdapter(videoAdapter);
            video_list_5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_6.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,6);
            video_list_1.setAdapter(videoAdapter);
            video_list_6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_7.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,7);
            video_list_1.setAdapter(videoAdapter);
            video_list_7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_8.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,8);
            video_list_1.setAdapter(videoAdapter);
            video_list_8.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_9.setVisibility(View.GONE);

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 9){

            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,4);
            video_list_1.setAdapter(videoAdapter);
            video_list_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_5.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,5);
            video_list_1.setAdapter(videoAdapter);
            video_list_5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_6.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,6);
            video_list_1.setAdapter(videoAdapter);
            video_list_6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_7.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,7);
            video_list_1.setAdapter(videoAdapter);
            video_list_7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_8.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,8);
            video_list_1.setAdapter(videoAdapter);
            video_list_8.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_9.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,9);
            video_list_1.setAdapter(videoAdapter);
            video_list_9.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_10.setVisibility(View.GONE);

            videolist(); ///IDK

        }else if (AllCat == 10){

            video_list_1.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,1);
            video_list_1.setAdapter(videoAdapter);
            video_list_1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_2.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,2);
            video_list_1.setAdapter(videoAdapter);
            video_list_2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_3.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,3);
            video_list_1.setAdapter(videoAdapter);
            video_list_3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_4.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,4);
            video_list_1.setAdapter(videoAdapter);
            video_list_4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_5.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,5);
            video_list_1.setAdapter(videoAdapter);
            video_list_5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_6.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,6);
            video_list_1.setAdapter(videoAdapter);
            video_list_6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_7.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,7);
            video_list_1.setAdapter(videoAdapter);
            video_list_7.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_8.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,8);
            video_list_1.setAdapter(videoAdapter);
            video_list_8.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_9.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,9);
            video_list_1.setAdapter(videoAdapter);
            video_list_9.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            video_list_10.setBackgroundColor(Color.parseColor("#00C09840"));
            videoAdapter = new VODItemList(videos, Video.this,10);
            video_list_1.setAdapter(videoAdapter);
            video_list_10.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
            video_list_10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            videolist(); ///IDK

        }


    }


    //get all data from rest
    public void videolist() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Video.this);
            //String url = "http://" + IPserver + "/vod/movie/data.json";
            String url = "http://46.100.60.180/vod/movie/json.php";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //VolleyLog.v("Response:%n %s", response.toString(4));

                        JSONArray students = response.getJSONArray("data");

                        if (!response.isNull("totalCategories")) {
                            AllCat = response.getInt("totalCategories");
                        }

                        // looping through All Students
                        for (int i = 0; i < students.length(); i++) {
                            JSONObject c = students.getJSONObject(i);

                            String Title = null;
                            String Url = null;
                            String UrlVOD = null;
                            String dis = null;
                            String cat = null;
                            int catN = 0;
                            if (!c.isNull("name")) {
                                Title = c.getString("name");
                            }
                            if (!c.isNull("thumbnail")) {
                                Url = c.getString("thumbnail");
                            }
                            if (!c.isNull("linkvod")) {
                                UrlVOD = c.getString("linkvod");
                            }
                            if (!c.isNull("description")) {
                                dis = c.getString("description");
                            }
                            if (!c.isNull("categories")) {
                                cat = c.getString("categories");
                            }
                            if (!c.isNull("categoriesNumber")) {
                                catN = c.getInt("categoriesNumber");
                            }
                            videos.add(new VideoItemList(Title, Url, UrlVOD,dis,cat,catN));
                        }
                        videoAdapter.notifyDataSetChanged();
                        videoAdapter.setLoading(false);
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
    public void listCN() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(Video.this);
            //String url = "http://" + IPserver + "/vod/movie/data.json";
            String url = "http://46.100.60.180/vod/movie/json.php";

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //VolleyLog.v("Response:%n %s", response.toString(4));


                        Log.e(" response ", String.valueOf(response));
                        if (!response.isNull("totalCategories")) {
                            AllCat = response.getInt("totalCategories");

                            init();
                        }

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


}
