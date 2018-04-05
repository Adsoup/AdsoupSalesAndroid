package com.example.asus.module_selectapp;

import android.app.Application;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<String> list_app_choose = new ArrayList<String>();
    public static int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView userInstalledApps = (ListView)findViewById(R.id.installed_app_list);
        List installedApps = getInstalledApps();
        AppAdapter installedAppAdapter = new AppAdapter(MainActivity.this, installedApps);
        userInstalledApps.setAdapter(installedAppAdapter);
        Button submit =(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ResultActivity.class);
                startActivity(i);
            }
        });
    }
    private List<AppList> getInstalledApps() {
        List<AppList> res = new ArrayList<AppList>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            String switchName = p.applicationInfo.packageName;
            if ((isSystemPackage(switchName) == true)) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                res.add(new AppList(appName, icon, switchName));
            }
        }
        return res;
    }

    private boolean isSystemPackage(String name) {
        ArrayList<String> pack_name = new ArrayList<String>();
        int pack_num = 0;
        pack_name.addAll(Arrays.asList("com.facebook.orca","jp.naver.line.android","com.twitter.android"//
                ,"com.skype.raider","com.tencent.mm","com.google.android.gm","com.facebook.katana"));
        if(name.equals("com.facebook.orca")){
            pack_num =1;
        }
        for (int i = 0; i < pack_name.size(); i++)
        {
            if (name.equals(pack_name.get(i))){
                pack_num = 1;
            }
        }
        return (pack_num) != 0;
    }

}
