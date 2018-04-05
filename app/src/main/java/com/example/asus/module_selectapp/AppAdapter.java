package com.example.asus.module_selectapp;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;
import static com.example.asus.module_selectapp.MainActivity.list_app_choose;

/**
 * Created by asus on 3/4/2561.
 */

public class AppAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<AppList> listStorage;
    private String text_id;

    public AppAdapter(Context context, List<AppList> customizedListView) {
        layoutInflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listStorage = customizedListView;
    }

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
        final int p =position;
        ViewHolder listViewHolder;
        if(convertView == null){
            listViewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.installed_app_list, parent, false);

            listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
            listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
            listViewHolder.switcherInListView = (Switch)convertView.findViewById(R.id.app_switch);
            convertView.setTag(listViewHolder);
        }else{
            listViewHolder = (ViewHolder)convertView.getTag();
        }
//        text_id=listStorage.get(position).getaSwitch();
        listViewHolder.textInListView.setText(listStorage.get(position).getName());
        listViewHolder.imageInListView.setImageDrawable(listStorage.get(position).getIcon());
//        listViewHolder.switcherInListView.setText(text_id);
        listViewHolder.switcherInListView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String text_id=listStorage.get(p).getaSwitch();
                if(isChecked){
                    list_app_choose.add(text_id);

                }else{
                    list_app_choose.remove(text_id);
                }
            }
        });
        return convertView;
    }
    static class ViewHolder{

        TextView textInListView;
        ImageView imageInListView;
        Switch switcherInListView;
    }
}
