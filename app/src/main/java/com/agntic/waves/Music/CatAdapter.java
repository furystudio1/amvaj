package com.agntic.waves.Music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * Created by root on 2/3/16.
 */
public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder> {

    private Context context;
    private List<CatModels> mobiles = new ArrayList<CatModels>();

    public CatAdapter(Context context, List<CatModels> mobiles) {
        this.context = context;
        this.mobiles = mobiles;
    }

    @Override
    public CatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.musiccatitem, null, false);
        final CatAdapter.ViewHolder viewHolder = new CatAdapter.ViewHolder(cardView);
        viewHolder.mobileImage = (ImageView) cardView.findViewById(R.id.img_cat_item_music);
        viewHolder.modelName = (TextView) cardView.findViewById(R.id.txt_cat_item_music);

        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.animate().scaleY(1.1f).scaleX(1.1f).start();
                    int position = viewHolder.getAdapterPosition();
                    CatModels movie = mobiles.get(position);



                }else {

                    v.animate().scaleY(1f).scaleX(1f).start();
                }
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                CatModels movie = mobiles.get(position);



            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CatAdapter.ViewHolder holder, int position) {

        ImageView mobileImageView = (ImageView) holder.mobileImage;

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.default_artwork);

        Glide.with(context)
                .asBitmap().load(mobiles.get(position).Url_img)
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

        public ViewHolder(View itemView) {
            super(itemView);
            mobileImage = (ImageView) itemView.findViewById(R.id.img_cat_item_music);
            modelName = (TextView) itemView.findViewById(R.id.txt_cat_item_music);
        }
    }

}
