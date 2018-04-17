package io.adsoup.sales.mobile;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppListActivity extends Activity {
    public static String KEY_APP_LIST = "selected_app_list";

    public static List<String> messengingAppList = new ArrayList<String>(Arrays.asList(
            "com.facebook.orca",
            "jp.naver.line.android",
            "com.twitter.android",
            "com.skype.raider",
            "com.tencent.mm",
            "com.google.android.gm",
            "com.facebook.katana",
            "com.firstrowria.pushnotificationtester"));

    public static List<String> choosenAppList = new ArrayList<String>();
    public static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_list_activity);
        ListView userInstalledApps = (ListView)findViewById(R.id.installed_app_list);
        List installedApps = getInstalledApps();

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        loadAppList(); // Important!! This line must come before AppAdapter as is has seq dependency.
        AppAdapter installedAppAdapter = new AppAdapter(AppListActivity.this, installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
    }

    private void setResultIntent() {
        Intent intent=new Intent();
        intent.putExtra(KEY_APP_LIST, choosenAppList.toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        setResultIntent();
        return true;
    }

    private List<AppItem> getInstalledApps() {
        List<AppItem> res = new ArrayList<AppItem>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            String switchName = p.applicationInfo.packageName;
            if ((isMessengingPackage(switchName))) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                res.add(new AppItem(appName, icon, switchName));
            }
        }
        return res;
    }

    private boolean isMessengingPackage(String name) {
        for(String pkgName : messengingAppList){
            if (pkgName.equals(name))
                return true;
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveAppList();
    }

    private void loadAppList() {
        //Save selected list
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        choosenAppList.clear();
        choosenAppList.addAll(sp.getStringSet("choosenAppList", new HashSet<String>()));
    }

    private void saveAppList() {
        //Save selected list
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();

        //Set the values
        Set<String> set = new HashSet<String>(choosenAppList);
        editor.putStringSet("choosenAppList", set);
        editor.apply();
    }

    class AppAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<AppItem> listStorage;
        private String text_id;

        public AppAdapter(Context context, List<AppItem> customizedListView) {
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
                convertView = layoutInflater.inflate(R.layout.app_item, parent, false);

                listViewHolder.textInListView = (TextView)convertView.findViewById(R.id.list_app_name);
                listViewHolder.imageInListView = (ImageView)convertView.findViewById(R.id.app_icon);
                listViewHolder.switcherInListView = (Switch)convertView.findViewById(R.id.app_switch);
                convertView.setTag(listViewHolder);
            }else{
                listViewHolder = (ViewHolder)convertView.getTag();
            }
            final AppItem appItem = listStorage.get(position);
            listViewHolder.textInListView.setText(appItem.getName());
            listViewHolder.imageInListView.setImageDrawable(appItem.getIcon());
            listViewHolder.switcherInListView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String pkgName =listStorage.get(p).getaSwitch();
                    if(isChecked){
                        if (!choosenAppList.contains(pkgName))
                        choosenAppList.add(pkgName );
                    }else{
                        if (choosenAppList.contains(pkgName))
                        choosenAppList.remove(pkgName );
                    }
                }
            });
            listViewHolder.switcherInListView.setChecked(choosenAppList.contains(appItem.getaSwitch()));
            return convertView;
        }

        class ViewHolder{

            TextView textInListView;
            ImageView imageInListView;
            Switch switcherInListView;
        }
    }

}
