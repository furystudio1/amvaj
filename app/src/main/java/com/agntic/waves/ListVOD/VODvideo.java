package com.agntic.waves.ListVOD;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.Dialog.Dialog;
import com.agntic.waves.ListVOD.adapters.AllAdapter;
import com.agntic.waves.ListVOD.adapters.BrandAdapter;
import com.agntic.waves.ListVOD.adapters.MobileAdapter;
import com.agntic.waves.ListVOD.models.Brand;
import com.agntic.waves.ListVOD.models.Mobile;
import com.agntic.waves.Login;
import com.agntic.waves.Main;
import com.agntic.waves.Model.VODItemList;
import com.agntic.waves.Model.VideoItemList;
import com.agntic.waves.R;
import com.agntic.waves.Video;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VODvideo extends AppCompatActivity {
    public static ImageView background_video;

    private ArrayList<Brand> brands = new ArrayList<Brand>();
    //private MobileAdapter MobileAdapter;

    SharedPreferences one_play_preferences;
    static String IPserver;
    int AllCat,language;
    //RecyclerView recyclerView;
    //private ExpandableListView mBrandsListView;

    //private ExpandableListAdapter mBrandAdapter;

    RecyclerView firstRecycleView;
    RecyclerView.Adapter adapter;
    TextView firstRecycleViewTitle;
    public static TextView video_name,con_video,des_video;
    LinearLayout parentView;
    public static String plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        one_play_preferences = getApplicationContext().getSharedPreferences("PROJECT_NAME", android.content.Context.MODE_PRIVATE);
        IPserver = one_play_preferences.getString("IPweb", "109.125.130.155:2560");
        language = one_play_preferences.getInt("Language", 1);

        if (language == 1){
            plus = "دسته بندی : ";
            setContentView(R.layout.listvideovod);
        }else if (language == 2){

            plus = "Genre : ";
            setContentView(R.layout.listvideovod_eng);
        }else if (language == 3){

            plus = "النوع: ";
            setContentView(R.layout.listvideovod);
        }

        background_video = findViewById(R.id.background_video_vod);


        //Picasso.get().load("https://s19.picofile.com/file/8439380626/z9a.jpg").fit().centerCrop().placeholder(R.drawable.main_bg).error(R.drawable.banner2).into(background_video);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.b1);

            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.b2)
                    //.diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(options)
                    .into(background_video);
                    //.into(background_video);
        //Glide.with(this).load("https://raygan.weblogtop.com/wp-content/uploads/sites/392/2021/08/Zakhme-Kari-11.jpg").into(background_video);

        //mBrandsListView = (ExpandableListView) findViewById(R.id.list_brands);


        //mBrandAdapter = new BrandAdapter(this, brands);
        //mBrandsListView.setAdapter(mBrandAdapter);



        //recyclerView = (RecyclerView) findViewById(R.id.RecycleList_vod);
        //recyclerView.setBackgroundColor(Color.parseColor("#00C09840"));
        //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        //recyclerView.setLayoutManager(layoutManager);
        //MobileAdapter = new MobileAdapter(this, brands);
        //recyclerView.setAdapter(MobileAdapter);



        parentView = (LinearLayout)findViewById(R.id.standard_view_parent);
        video_name = findViewById(R.id.video_name);
        con_video = findViewById(R.id.con_video);
        des_video = findViewById(R.id.des_video);

        //data();
        videolist();

        //Get file font
        Typeface typeface = Typeface.createFromAsset(getAssets(), "IRANSansMobile.ttf");

        video_name.setTypeface(typeface);
        con_video.setTypeface(typeface);
        des_video.setTypeface(typeface);
    }




    //get all data from rest
    public void videolist() {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(VODvideo.this);
            String url = "http://" + IPserver + "/Amvaj/movie/json.php";
            //String url = "https://amvaj.airfinance.org/VOD/movie/json.php";

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

                            String Categories = null;
                            int catN = 0;

                            if (language == 1){
                                //Farsi
                                if (!c.isNull("categories")) {
                                    Categories = c.getString("categories");
                                }
                            }else if (language == 2){
                                //ENG
                                if (!c.isNull("categoriesEng")) {
                                    Categories = c.getString("categoriesEng");
                                }
                            }else if (language == 3){
                                //Arabic
                                if (!c.isNull("categoriesAR")) {
                                    Categories = c.getString("categoriesAR");
                                }
                            }


                            JSONArray students2 = c.getJSONArray("data");

                            List<Mobile> oneplusMobiles = new ArrayList<Mobile>();

                            for (int i2 = 0; i2 < students2.length(); i2++) {

                                String Title = null;
                                String Url = null;
                                String UrlVOD = null;
                                String dis = null;
                                String cat = null;
                                String thumbnailBG = null;
                                JSONObject c2 = students2.getJSONObject(i2);



                                if (language == 1){
                                    //Farsi
                                    if (!c2.isNull("name")) {
                                        Title = c2.getString("name");
                                    }
                                    if (!c2.isNull("thumbnail")) {
                                        Url = c2.getString("thumbnail");
                                    }
                                    if (!c2.isNull("linkvod")) {
                                        UrlVOD = c2.getString("linkvod");
                                    }
                                    if (!c2.isNull("description")) {
                                        dis = c2.getString("description");
                                    }
                                    if (!c2.isNull("categories")) {
                                        cat = c2.getString("categories");
                                    }
                                    if (!c2.isNull("categoriesNumber")) {
                                        catN = c2.getInt("categoriesNumber");
                                    }
                                    if (!c2.isNull("thumbnailBG")) {
                                        thumbnailBG = c2.getString("thumbnailBG");
                                    }
                                }else if (language == 2){
                                    //Eng
                                    if (!c2.isNull("nameEng")) {
                                        Title = c2.getString("nameEng");
                                    }
                                    if (!c2.isNull("thumbnail")) {
                                        Url = c2.getString("thumbnail");
                                    }
                                    if (!c2.isNull("linkvod")) {
                                        UrlVOD = c2.getString("linkvod");
                                    }
                                    if (!c2.isNull("descriptionEng")) {
                                        dis = c2.getString("descriptionEng");
                                    }
                                    if (!c2.isNull("categoriesEng")) {
                                        cat = c2.getString("categoriesEng");
                                    }
                                    if (!c2.isNull("categoriesNumber")) {
                                        catN = c2.getInt("categoriesNumber");
                                    }
                                    if (!c2.isNull("thumbnailBG")) {
                                        thumbnailBG = c2.getString("thumbnailBG");
                                    }
                                }else if (language == 3){
                                    //Arabic
                                    if (!c2.isNull("nameAR")) {
                                        Title = c2.getString("nameAR");
                                    }
                                    if (!c2.isNull("thumbnail")) {
                                        Url = c2.getString("thumbnail");
                                    }
                                    if (!c2.isNull("linkvod")) {
                                        UrlVOD = c2.getString("linkvod");
                                    }
                                    if (!c2.isNull("descriptionAR")) {
                                        dis = c2.getString("descriptionAR");
                                    }
                                    if (!c2.isNull("categoriesAR")) {
                                        cat = c2.getString("categoriesAR");
                                    }
                                    if (!c2.isNull("categoriesNumber")) {
                                        catN = c2.getInt("categoriesNumber");
                                    }
                                    if (!c2.isNull("thumbnailBG")) {
                                        thumbnailBG = c2.getString("thumbnailBG");
                                    }
                                }
                                oneplusMobiles.add(new Mobile(Title, Url, UrlVOD,dis,cat,catN,thumbnailBG));
                            }
                            Brand oneplus = new Brand(Categories, oneplusMobiles);
                            brands.add(oneplus);
                        }
                        //MobileAdapter.notifyDataSetChanged();
                       //videoAdapter.setLoading(false);
                    } catch (JSONException e) {
                        Log.e("  error ", String.valueOf(e));
                        e.printStackTrace();
                    }


                    for (int i = 0; i < brands.size(); i++) {
                        View view = getLayoutInflater().inflate(R.layout.listvideovoditem, null);
                        firstRecycleViewTitle = (TextView) view.findViewById(R.id.text_list_vod_child);
                        firstRecycleViewTitle.setText(brands.get(i).brandName);
                        firstRecycleViewTitle.setLayoutParams(
                                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        firstRecycleView = (RecyclerView) view.findViewById(R.id.RecycleList_vod_child);
                        firstRecycleView.setHasFixedSize(true);
                        firstRecycleView.setLayoutManager(
                                new LinearLayoutManager(VODvideo.this, LinearLayoutManager.HORIZONTAL,
                                        false));

                        adapter = new MobileAdapter(VODvideo.this,brands.get(i).mobiles);
                        firstRecycleView.setAdapter(adapter);
                        parentView.addView(view);
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("  All Json loge ", "eerror");

                            Intent uou = new Intent(VODvideo.this, Dialog.class);
                            uou.putExtra("what",5);
                            startActivity(uou);
                            VODvideo.this.finish();

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


    private void data() {
        //List<Mobile> oneplusMobiles = new ArrayList<Mobile>();
        //brands.add(new Mobile("a", "https://s19.picofile.com/file/8439380626/z9a.jpg", "$250.00","","",1));
        //brands.add(new Mobile("b", "https://s19.picofile.com/file/8439380626/z9a.jpg", "$349.00","","",1));
        //brands.add(new Mobile("c", "https://s19.picofile.com/file/8439380626/z9a.jpg", "$230.00","","",1));
        //brands.add(new Mobile("d", "https://s19.picofile.com/file/8439380626/z9a.jpg", "$250.00","","",1));
        //brands.add(new Mobile("f", "https://s19.picofile.com/file/8439380626/z9a.jpg", "$349.00","","",1));
        //brands.add(new Mobile("g", "https://s19.picofile.com/file/8439380626/z9a.jpg", "$230.00","","",1));
        //Brand oneplus = new Brand("OnePlus", oneplusMobiles);

        //List<Mobile> nexusMobiles = new ArrayList<Mobile>();
        //nexusMobiles.add(new Mobile("aa", "https://i.pinimg.com/originals/0d/83/a7/0d83a78aa64ff5388ce7f5128eea75c7.jpg", "$250.00","","",1));
        //nexusMobiles.add(new Mobile("bb", "https://i.pinimg.com/originals/0d/83/a7/0d83a78aa64ff5388ce7f5128eea75c7.jpg", "$349.00","","",1));
        //nexusMobiles.add(new Mobile("cc", "https://i.pinimg.com/originals/0d/83/a7/0d83a78aa64ff5388ce7f5128eea75c7.jpg", "$230.00","","",1));
        //nexusMobiles.add(new Mobile("dd", "https://i.pinimg.com/originals/0d/83/a7/0d83a78aa64ff5388ce7f5128eea75c7.jpg", "$250.00","","",1));
        //nexusMobiles.add(new Mobile("ff", "https://i.pinimg.com/originals/0d/83/a7/0d83a78aa64ff5388ce7f5128eea75c7.jpg", "$349.00","","",1));
        //nexusMobiles.add(new Mobile("gg", "https://i.pinimg.com/originals/0d/83/a7/0d83a78aa64ff5388ce7f5128eea75c7.jpg", "$230.00","","",1));
        //Brand nexus = new Brand("Google Nexus", nexusMobiles);

        //brands.add(oneplus);
        //brands.add(nexus);
        //MobileAdapter.notifyDataSetChanged();
        //videoAdapter.setLoading(false);
    }

    @Override
    public void onBackPressed() {

        Intent uou = new Intent(VODvideo.this, Main.class);
        uou.putExtra("from",2);
        startActivity(uou);

        finish();
        super.onBackPressed();

    }
}
