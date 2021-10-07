package com.agntic.waves.ListVOD.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.ListVOD.VODdetailVideo;
import com.agntic.waves.ListVOD.VODvideo;
import com.agntic.waves.ListVOD.models.Mobile;
import com.agntic.waves.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/3/16.
 */
public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.ViewHolder> {

    private Context context;
    private List<Mobile> mobiles = new ArrayList<Mobile>();

    public MobileAdapter(Context context, List<Mobile> mobiles) {
        this.context = context;
        this.mobiles = mobiles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.videoitem, null, false);
        final ViewHolder viewHolder = new ViewHolder(cardView);
        viewHolder.mobileImage = (ImageView) cardView.findViewById(R.id.img_item_video);
        viewHolder.modelName = (TextView) cardView.findViewById(R.id.txt_item_video);
        viewHolder.item_video_shadow = (TextView) cardView.findViewById(R.id.item_video_shadow);

        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.05f).scaleX(1.05f).start();
                    int position = viewHolder.getAdapterPosition();
                    Mobile movie = mobiles.get(position);

                    RequestOptions options = new RequestOptions()
                            .centerCrop()
                            .error(R.drawable.b2);

                    Glide.with(context)
                            .load(Uri.parse(movie.getthumbnailBG()))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(options)
                            .into(VODvideo.background_video);

                    VODvideo.video_name.setText(movie.getName());
                    VODvideo.con_video.setText(VODvideo.plus + movie.getCat());
                    VODvideo.des_video.setText(movie.getDis());
                    viewHolder.item_video_shadow.setBackgroundResource(R.color.color_video_music_on);
                }else {

                    v.animate().scaleY(1f).scaleX(1f).start();
                    viewHolder.item_video_shadow.setBackgroundResource(R.color.color_video_music_off);
                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Mobile movie = mobiles.get(position);

                Intent i = new Intent(context, VODdetailVideo.class);
                i.putExtra("name",movie.getName());
                i.putExtra("dis",movie.getDis());
                i.putExtra("nameEng","");
                i.putExtra("url_img",movie.getUrl_img());
                i.putExtra("url_img_bg",movie.getthumbnailBG());
                i.putExtra("url_video",movie.getUrl_video());
                i.putExtra("cat",movie.getCat());
                context.startActivity(i);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ImageView mobileImageView = (ImageView) holder.mobileImage;

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.video1);

        Glide.with(context)
                .asBitmap().load(mobiles.get(position).getUrl_img())
                .apply(options).into(mobileImageView);

        TextView modelTextView = (TextView) holder.modelName;
        modelTextView.setText(mobiles.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mobiles.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mobileImage;
        TextView modelName;
        TextView item_video_shadow;

        public ViewHolder(View itemView) {
            super(itemView);
            mobileImage = (ImageView) itemView.findViewById(R.id.img_item_video);
            modelName = (TextView) itemView.findViewById(R.id.txt_item_video);
            item_video_shadow = (TextView) itemView.findViewById(R.id.item_video_shadow);
        }
    }

}
