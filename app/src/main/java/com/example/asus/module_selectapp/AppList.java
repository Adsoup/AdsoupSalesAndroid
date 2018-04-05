package com.example.asus.module_selectapp;

import android.graphics.drawable.Drawable;
import android.widget.Switch;

/**
 * Created by asus on 3/4/2561.
 */

public class AppList {
    private String name;
    Drawable icon;
    String aSwitch;

    public AppList(String name, Drawable icon, String aSwitch) {
        this.name = name;
        this.icon = icon;
        this.aSwitch = aSwitch;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getaSwitch() {
        return aSwitch;
    }
}
