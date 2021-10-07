package com.agntic.waves.ListVOD.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.ListVOD.models.Brand;
import com.agntic.waves.ListVOD.models.Mobile;
import com.agntic.waves.Main;
import com.agntic.waves.Model.OnLoadMoreLisener;
import com.agntic.waves.Model.Video;
import com.agntic.waves.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {

    private Context context;
    private List<Brand> mobiles = new ArrayList<Brand>();

    public AllAdapter(Context context, List<Brand> mobiles) {
        this.context = context;
        this.mobiles = mobiles;
    }

    @Override
    public AllAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View cardView = inflater.inflate(R.layout.listvideovoditem, null, false);
        AllAdapter.ViewHolder viewHolder = new AllAdapter.ViewHolder(cardView);
        viewHolder.Name = (TextView) cardView.findViewById(R.id.text_list_vod_child);
        viewHolder.recyclerView = (RecyclerView) cardView.findViewById(R.id.RecycleList_vod_child);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AllAdapter.ViewHolder holder, int position) {

        TextView modelTextView = (TextView) holder.Name;
        modelTextView.setText(mobiles.get(position).brandName);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.recyclerView.setLayoutManager(llm);
        MobileAdapter horizontalListAdapter = new MobileAdapter(context, mobiles.get(position).mobiles);
        holder.recyclerView.setAdapter(horizontalListAdapter);

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

        TextView Name;
        RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            Name = (TextView)itemView.findViewById(R.id.text_list_vod_child);
            recyclerView = (RecyclerView)itemView.findViewById(R.id.RecycleList_vod_child);
        }
    }

    /**List<Brand> brands = new ArrayList<Brand>();
    Context context;
    OnLoadMoreLisener onLoadMoreLisener;
    int r = 1;
    boolean isLoading = false;

    public AllAdapter(List<Brand> videoList, Context context, RecyclerView recyclerView) {
        this.brands = videoList;
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
    public com.agntic.waves.ListVOD.adapters.AllAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
            try {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listvideovoditem,parent,false);
            } catch (Exception e) {
                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                e.printStackTrace();
            }

        return new com.agntic.waves.ListVOD.adapters.AllAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.agntic.waves.ListVOD.adapters.AllAdapter.VideoViewHolder holder, int position) {

        //Get file font
        Typeface typeface = Typeface.createFromAsset(context.getAssets(), "IRANSansMobile.ttf");

        final Brand video = brands.get(position);
        holder.Name.setText(video.brandName);

        try {
            //Set Font
            holder.Name.setTypeface(typeface);
        } catch (Exception e) {
            Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
            e.printStackTrace();
        }

        //LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        //holder.recyclerView.setLayoutManager(layoutManager);

        //MobileAdapter horizontalListAdapter = new MobileAdapter(context, brands.get(position).mobiles);
        //holder.recyclerView.setAdapter(horizontalListAdapter);

    }

    @Override
    public int getItemCount() {
        return brands.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView Name;
        //RecyclerView recyclerView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            try {
                Name = (TextView)itemView.findViewById(R.id.text_list_vod_child);
                //recyclerView = (RecyclerView)itemView.findViewById(R.id.RecycleList_vod_child);
            } catch (Exception e) {
                Log.e("  error ", String.valueOf(e)); /////////////////////////////////////////////////////////////////////////////////
                e.printStackTrace();
            }
        }
    }**/

}
