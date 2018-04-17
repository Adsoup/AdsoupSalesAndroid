package io.adsoup.sales.mobile;

import android.graphics.drawable.Drawable;

public class AppItem {
    private String name;
    Drawable icon;
    String aSwitch;

    public AppItem(String name, Drawable icon, String aSwitch) {
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
