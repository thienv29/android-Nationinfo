package com.android.devthien.nationinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.devthien.nationinfo.models.NationModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterListView extends BaseAdapter {

    private List<NationModel> nationModelList;
    private Context context;
    private LayoutInflater layoutInflater;

    public AdapterListView(List<NationModel> images, Context context){
        this.nationModelList = images;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return nationModelList.size();
    }

    @Override
    public Object getItem(int i) {
        return nationModelList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ViewHolder viewHolder;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.list_view_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.imgFlag = v.findViewById(R.id.image_item);
            viewHolder.txtCountryName = v.findViewById(R.id.image_name);
            viewHolder.txtCapital = v.findViewById(R.id.image_des);

            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }
        NationModel item = (NationModel) getItem(i);
        viewHolder.txtCountryName.setText(item.getCountryName());
        viewHolder.txtCapital.setText(item.getCapital());
        Picasso.with(context).load(item.getUrlFlag()).into(viewHolder.imgFlag);

        return v;
    }

    public void updateReceiptsList(List<NationModel> newlist) {
        nationModelList.clear();
        nationModelList.addAll(newlist);
        this.notifyDataSetChanged();
    }
    private static class ViewHolder {
        ImageView imgFlag;
        TextView txtCountryName;
        TextView txtCapital;
    }
}
