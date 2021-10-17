package com.agntic.waves.Model;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.ListVOD.VODvideo;
import com.agntic.waves.ListVOD.models.Mobile;
import com.agntic.waves.Main;
import com.agntic.waves.R;
import com.agntic.waves.Stream;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by FURY on 12/1/2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    public static List<Video> videoList;
    public static int pos = 0;
    Context context;
    OnLoadMoreLisener onLoadMoreLisener;
    int r = 1;
    boolean isLoading = false;

    public VideoAdapter(List<Video> videoList, Context context, RecyclerView recyclerView) {
        this.videoList = videoList;
        this.context = context;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int total = linearLayoutManager.getItemCount();
                int lastVisible = linearLayoutManager.findLastVisibleItemPosition();
                if (isLoading == false && total <= lastVisible + 4){
                    if (onLoadMoreLisener != null){
                        onLoadMoreLisener.OnLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }

    public void setOnLoadMoreLisener(OnLoadMoreLisener onLoadMoreLisener) {
        this.onLoadMoreLisener = onLoadMoreLisener;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(r == 1){
            r = 2;
            try {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
            } catch (Exception e) {
                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                e.printStackTrace();
            }
        }else if (r == 2){
            r = 1;
            try {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
            } catch (Exception e) {
                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                e.printStackTrace();
            }
        }

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile.ttf");
        pos = position;
        final Video video = videoList.get(position);
        holder.Name.setText(video.getName());
        holder.Numb.setText(video.getNumb());

        try {
        //Set Font
        holder.Name.setTypeface(typeface);
        holder.Numb.setTypeface(typeface);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        holder.btnItemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to Show Recipe
                /**Intent show = new Intent(context, Recipe.class);
                 String id = String.valueOf(video.getId());
                 show.putExtra("ID", id);
                 context.startActivity(show);**/
                try {
                Main.TV(video.getUrl(),video.getName());
                } catch (Exception e) {
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }
            }
        });


        holder.btnItemVideo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                    holder.Name.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                }else {

                    v.animate().scaleY(1f).scaleX(1f).start();
                    holder.Name.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView Name;
        TextView Numb;
        LinearLayout btnItemVideo;

        public VideoViewHolder(View itemView) {
            super(itemView);
            try {
            Name = (TextView)itemView.findViewById(R.id.nameChannel);
            Numb = (TextView)itemView.findViewById(R.id.numberChannel);
            btnItemVideo = (LinearLayout) itemView.findViewById(R.id.btnItemChannel);
            } catch (Exception e) {
                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                e.printStackTrace();
            }
        }
    }

}
