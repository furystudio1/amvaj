package com.agntic.waves.ListVOD.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.agntic.waves.ListVOD.models.Brand;
import com.agntic.waves.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2/3/16.
 */
public class BrandAdapter implements ExpandableListAdapter {

    private Context context;
    private List<Brand> brands = new ArrayList<Brand>();

    public BrandAdapter(Context context, List<Brand> brands) {
        this.context = context;
        this.brands = brands;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return brands.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return brands.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return brands.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ParentHolder parentHolder = null;

        Brand brand = (Brand) getGroup(groupPosition);

        if(convertView == null) {
            LayoutInflater userInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = userInflater.inflate(R.layout.listvideovoditem, null);
            convertView.setHorizontalScrollBarEnabled(true);

            parentHolder = new ParentHolder();
            convertView.setTag(parentHolder);

        } else {
            parentHolder = (ParentHolder) convertView.getTag();
        }

        parentHolder.brandName = (TextView) convertView.findViewById(R.id.text_list_vod_child);
        parentHolder.brandName.setText(brand.brandName);

        //parentHolder.indicator = (ImageView) convertView.findViewById(R.id.image_indicator);

        if(isExpanded) {
            //parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_up_black_18dp);
        } else {
           //parentHolder.indicator.setImageResource(R.drawable.ic_keyboard_arrow_down_black_18dp);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildHolder childHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.itemchild, parent, false);
            childHolder = new ChildHolder();
            convertView.setTag(childHolder);
        }
        else {
            childHolder = (ChildHolder) convertView.getTag();
        }

        childHolder.horizontalListView = (RecyclerView) convertView.findViewById(R.id.mobiles);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        childHolder.horizontalListView.setLayoutManager(layoutManager);

        MobileAdapter horizontalListAdapter = new MobileAdapter(context, brands.get(groupPosition).mobiles);
        childHolder.horizontalListView.setAdapter(horizontalListAdapter);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    private static class ChildHolder {
        static RecyclerView horizontalListView;
    }

    private static class ParentHolder {
        TextView brandName;
        ImageView indicator;
    }
}
