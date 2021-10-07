package com.agntic.waves.Music;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.ListVOD.VODvideo;
import com.agntic.waves.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.exoplayer2.MediaItem;

import org.json.JSONException;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * Created by root on 2/3/16.
 */
public class MusicAdapter extends RecyclerView.Adapter<com.agntic.waves.Music.MusicAdapter.ViewHolder> {

    private Context context;
    public static List<MusicModels> mobiles = new ArrayList<>();
    public static int pos = 0;

    public MusicAdapter(Context context, List<MusicModels> mobiles) {
        this.context = context;
        this.mobiles.clear();
        this.mobiles = new ArrayList<>();
        this.mobiles = mobiles;
    }

    @Override
    public com.agntic.waves.Music.MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.musicitem, null, false);
        final com.agntic.waves.Music.MusicAdapter.ViewHolder viewHolder = new com.agntic.waves.Music.MusicAdapter.ViewHolder(cardView);
        viewHolder.mobileImage = (ImageView) cardView.findViewById(R.id.img_item_music);
        viewHolder.modelName = (TextView) cardView.findViewById(R.id.txt_item_music);
        viewHolder.item_music_shadow = (TextView) cardView.findViewById(R.id.item_music_shadow);


        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){

                    try {
                    v.animate().scaleY(1.05f).scaleX(1.05f).start();
                    } catch (Exception e) {
                        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                        e.printStackTrace();
                    }
                    int position = viewHolder.getAdapterPosition();

                    try {

                    Glide.with(context)
                            .load(mobiles.get(position).getAuthor())
                            .transition(DrawableTransitionOptions.withCrossFade())
                            //.diskCacheStrategy(DiskCacheStrategy.ALL)
                            .apply(bitmapTransform(new BlurTransformation(42)))
                            .into(Music.img_bg_music);
                    } catch (Exception e) {
                        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                        e.printStackTrace();
                    }
                    viewHolder.item_music_shadow.setBackgroundResource(R.color.color_video_music_on);
                }else {
                    try {
                    v.animate().scaleY(1f).scaleX(1f).start();
                        viewHolder.item_music_shadow.setBackgroundResource(R.color.color_video_music_off);
                    } catch (Exception e) {
                        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                        e.printStackTrace();
                    }
                }
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Music.ne = true;
                int position = viewHolder.getAdapterPosition();

                Music.music_title.setText(mobiles.get(position).getName());
                Music.music_create.setText(mobiles.get(position).getArtist());
                try {
                /**List<MusicManager.Music> musicList = new ArrayList<>();

                MusicManager.Music music = new MusicManager.Music(mobiles.get(position).Name, mobiles.get(position).Url_img, mobiles.get(position).artist, mobiles.get(position).author);

                musicList.add(music);

                Music.mManager.setPlayList(musicList);
                Music.mManager.startPlay(0);
                Music.initMusicManager(false);*/

                    Music.exoPlayer.clearMediaItems();
                    MediaItem mediaItem = MediaItem.fromUri(mobiles.get(position).getUrl_img());

                    Music.exoPlayer.setMediaItem(mediaItem);
                    Music.exoPlayer.prepare();
                    Music.exoPlayer.setPlayWhenReady(true);
                    if (mobiles.get(position).getAuthor() != null) {

                        RequestOptions options = new RequestOptions()
                                .centerCrop()
                                .error(R.drawable.default_artwork);

                        Glide.with(context)
                                .asBitmap().load(mobiles.get(position).getAuthor())
                                .apply(options).into(Music.ivCover);

                    } else {
                        Music.ivCover.setImageResource(R.drawable.default_artwork);
                    }

                } catch (Exception e) {
                    Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                    e.printStackTrace();
                }
                pos = position;

                Music.ne = false;
            }
        });

        Music.music_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Music.repeater){
                    pos = pos <= 0 ? mobiles.size() - 1 : pos - 1;
                }else {
                    pos = pos <= 0 ? mobiles.size() - 1 : pos - 2;
                }
                    Music.music_title.setText(mobiles.get(pos).getName());
                    Music.music_create.setText(mobiles.get(pos).getArtist());
                    try {

                        Music.exoPlayer.clearMediaItems();
                        MediaItem mediaItem = MediaItem.fromUri(mobiles.get(pos).getUrl_img());

                        Music.exoPlayer.setMediaItem(mediaItem);
                        Music.exoPlayer.prepare();
                        Music.exoPlayer.setPlayWhenReady(true);

                        if (mobiles.get(pos).getAuthor() != null) {

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .error(R.drawable.default_artwork);

                            Glide.with(context)
                                    .asBitmap().load(mobiles.get(pos).getAuthor())
                                    .apply(options).into(Music.ivCover);

                        } else {
                            Music.ivCover.setImageResource(R.drawable.default_artwork);
                        }

                        notifyItemChanged(pos);

                        viewHolder.itemView.hasFocus();
                        viewHolder.itemView.isFocused();

                    } catch (Exception e) {
                        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                        e.printStackTrace();
                    }

            }
        });

        Music.music_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Music.repeater){
                    pos = pos >= mobiles.size() - 1 ? 0 : pos + 1;
                }
                    Music.music_title.setText(mobiles.get(pos).getName());
                    Music.music_create.setText(mobiles.get(pos).getArtist());
                    try {
                        Music.exoPlayer.clearMediaItems();

                        MediaItem mediaItem = MediaItem.fromUri(mobiles.get(pos).getUrl_img());

                        Music.exoPlayer.setMediaItem(mediaItem);
                        Music.exoPlayer.prepare();
                        Music.exoPlayer.setPlayWhenReady(true);

                        if (mobiles.get(pos).getAuthor() != null) {

                            RequestOptions options = new RequestOptions()
                                    .centerCrop()
                                    .error(R.drawable.default_artwork);

                            Glide.with(context)
                                    .asBitmap().load(mobiles.get(pos).getAuthor())
                                    .apply(options).into(Music.ivCover);

                        } else {
                            Music.ivCover.setImageResource(R.drawable.default_artwork);
                        }

                        notifyItemChanged(pos);

                    } catch (Exception e) {
                        Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                        e.printStackTrace();
                    }

            }
        });



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(com.agntic.waves.Music.MusicAdapter.ViewHolder holder, int position) {

        ImageView mobileImageView = (ImageView) holder.mobileImage;

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.default_artwork);

        Glide.with(context)
                .asBitmap().load(mobiles.get(position).getAuthor())
                .apply(options).into(mobileImageView);


        /*if (mCurrentMusic2.albumCover != null) {
            mobileImageView.setImageBitmap(
                    BitmapFactory.decodeByteArray(
                            mobiles.get(position).albumCover, 0, mobiles.get(position).albumCover.length
                    )
            );
        } else {
            mobileImageView.setImageResource(R.drawable.default_artwork);
        }**/


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
        TextView item_music_shadow;

        public ViewHolder(View itemView) {
            super(itemView);
            mobileImage = (ImageView) itemView.findViewById(R.id.img_item_music);
            modelName = (TextView) itemView.findViewById(R.id.txt_item_music);
            item_music_shadow = (TextView) itemView.findViewById(R.id.item_music_shadow);
        }
    }

}
