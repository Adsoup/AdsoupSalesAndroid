package com.example.asus.module_selectapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

import static com.example.asus.module_selectapp.MainActivity.list_app_choose;

/**
 * Created by asus on 5/4/2561.
 */

public class ResultAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<String> listStorage;

    @Override
    public int getCount() {
        return listStorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.choose_list, parent, false);
            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.choose_list);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        listViewHolder.textInListView.setText(listStorage.get(position));

        return convertView;
    }

    public ResultAdapter(Context context, List<String> customizedListView) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

    static class ViewHolder{
        TextView textInListView;

    }
}
