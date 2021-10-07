package com.agntic.waves.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.agntic.waves.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import cn.septenary.widget.ViewHolder;

/**
 * Created by FURY on 12/1/2017.
 */

public class VODItemList extends BaseAdapter {

    private final Context mContext;
    private Bitmap bmp;
    boolean isLoading = false;
    int where = 0;
    List<VideoItemList> videoList;
    public VODItemList(List<VideoItemList> videoList, Context context,int where) {
        this.videoList = videoList;
        this.mContext = context;
        this.where = where;



    }

    public boolean isLoading() {
        return isLoading;
    }
    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Override
    public String getItem(int position) {
        return "Pos:" + position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "IRANSansMobile.ttf");


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.videoitem, parent, false);
            //convertView.setBackgroundColor(0xff << 24 | new Random().nextInt(0xffffff));
        }

        TextView tv = ViewHolder.getView(convertView, R.id.txt_item_video);
        final ImageView img_item_video = ViewHolder.getView(convertView, R.id.img_item_video);

        tv.setTypeface(typeface);
        tv.setTypeface(typeface);

        if (where == 1){

            if (position == 9){


            }else {

                final VideoItemList video = videoList.get(position);
                tv.setText(video.getName());
                try {
                    //Set Font
                    tv.setTypeface(typeface);
                } catch (Exception e) {
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }

                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.main_bg);
                Glide.with(mContext).load(video.getUrl_img()).apply(options).into(img_item_video);
                //video.setPosition(position);

            }

        }else{

            if (position == 9){


            }else {

                for (int i = 0; i < videoList.size(); i++) {
                    final VideoItemList video = videoList.get(i);
                    if (where == video.getTotalCat()) {

                        tv.setText(video.getName());
                        try {
                            //Set Font
                            tv.setTypeface(typeface);
                        } catch (Exception e) {
                            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                            e.printStackTrace();
                        }
                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .error(R.drawable.main_bg);
                        Glide.with(mContext).load(video.getUrl_img()).apply(options).into(img_item_video);
                        //video.setPosition(position);

                    }
                }
            }
        }



        //Glide.with(mContext).load("https://s18.picofile.com/file/8439380542/z10a.jpg").centerCrop().placeholder(R.drawable.main_bg).into(img_item_video);


        return convertView;
    }
}
