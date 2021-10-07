package com.agntic.waves.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
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

public class TestAdapter extends BaseAdapter {

    private final Context mContext;
    private Bitmap bmp;
    boolean isLoading = false;
    List<VideoItemList> videoList;
    public TestAdapter(List<VideoItemList> videoList, Context context) {
        this.videoList = videoList;
        this.mContext = context;
    }

    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public int getCount() {
        return 10;
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

        final VideoItemList video = videoList.get(position);


        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.videoitem, parent, false);
            //convertView.setBackgroundColor(0xff << 24 | new Random().nextInt(0xffffff));
        }
        TextView tv = ViewHolder.getView(convertView, R.id.txt_item_video);
        tv.setText(getItem(position));
        final ImageView img_item_video = ViewHolder.getView(convertView, R.id.img_item_video);


        //holder.Name.setText(video.getName());
        //holder.Numb.setText(video.getNumb());

        if (position == 1){

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .error(R.drawable.main_bg);
            Glide.with(mContext).load("https://s18.picofile.com/file/8439380542/z10a.jpg").apply(options).into(img_item_video);

        }

        return convertView;
    }
}
